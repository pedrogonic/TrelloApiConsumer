package com.pedrogonic.trelloapiconsumer.sprintPlanning.service;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloCard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloChecklist;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloList;
import com.pedrogonic.trelloapiconsumer.service.TrelloService;
import com.pedrogonic.trelloapiconsumer.sprintPlanning.model.SprintPlanningResultServiceBoardInfo;
import com.pedrogonic.trelloapiconsumer.sprintPlanning.model.response.SprintPlanningResultServiceResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SprintPlanningResultService extends TrelloService {

    private static String TOTAL_HOURS_PARAMETER_MARK = "%TOTAL_HOURS%";
    private static String OUTPUT_PATH = "D:/output.html";

    public SprintPlanningResultServiceResponseBody run(String boardUrl, Double multiplier, String prependText, String appendText) {

        log.info("Rodando serviço para exportação do resultado da planning!");

        SprintPlanningResultServiceBoardInfo boardInfo = new SprintPlanningResultServiceBoardInfo();
        boardInfo.setShortUrl(boardUrl);

        boardInfo.setId(getBoardIdBoardInfo(boardInfo));
        boardInfo.setSprintListId(getBoardListIdByName(boardInfo, "Sprint"));

        List<TrelloCard> boardCards = extractCards(boardInfo);

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<ul>");

        boardCards.forEach( card -> {
            card.setHours( card.getHours() * multiplier );
            stringBuilder.append("<li>" + card.getName() + " - " + card.getHours() + " horas</li>"); // TODO: localization
        });


        stringBuilder.append("</ul>");

        Double totalHours = boardCards.stream().map( card -> card.getHours()).reduce(0.0, Double::sum);

        String cardsText = stringBuilder.toString();

        prependText = prependText.replaceAll(TOTAL_HOURS_PARAMETER_MARK, totalHours+"");
        appendText = appendText.replaceAll(TOTAL_HOURS_PARAMETER_MARK, totalHours+"");

        String text = prependText + "<br/><br/>" + cardsText + "<br/><br/>" + appendText;

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(OUTPUT_PATH);

        try {
            if (file == null)
                file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);

            writer.close();

            log.info("Atualizando arquivo " + OUTPUT_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SprintPlanningResultServiceResponseBody(text, totalHours,boardCards);
    }

    // TODO pull up
    private String getBoardIdBoardInfo(SprintPlanningResultServiceBoardInfo boardInfo) {
        return restTemplate.getForObject(TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/?" + API_AND_TOKEN_PARAMS
                , TrelloBoard.class).getId();
    }

    private String getBoardListIdByName(SprintPlanningResultServiceBoardInfo boardInfo, String listName) {
        TrelloList[] trelloLists = restTemplate.getForObject(
                TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/lists?" + API_AND_TOKEN_PARAMS
                , TrelloList[].class);

        TrelloList trelloList = Arrays.stream(trelloLists).filter(tl -> tl.getName().equalsIgnoreCase(listName)).collect(Collectors.toList()).get(0);

        return trelloList.getId();
    }
    private List<TrelloCard> extractCards(SprintPlanningResultServiceBoardInfo boardInfo) {
        try {

            List<TrelloCard> trelloCards = new ArrayList<>(Arrays.asList(
                    restTemplate.getForObject(
                            TRELLO_API_URL + "lists/" + boardInfo.getSprintListId() + "/cards/?cards=open&checklists=all&" + API_AND_TOKEN_PARAMS
                            , TrelloCard[].class)
            ));

            for(TrelloCard card: trelloCards) {

                if (card.extractHoursFromName() == 0) {

                    for (TrelloChecklist trelloChecklist : card.getChecklistList()) {
                        card.setHours(trelloChecklist.getTrelloCheckItems().stream().map(
                                item -> item.extractHoursFromName()
                        ).reduce(0.0, Double::sum));
                    }

                } else
                    card.setHours(card.extractHoursFromName());
            }

            return trelloCards;

        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


}
