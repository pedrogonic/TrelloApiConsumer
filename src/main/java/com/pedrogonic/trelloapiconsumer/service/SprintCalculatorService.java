package com.pedrogonic.trelloapiconsumer.service;

import com.pedrogonic.trelloapiconsumer.config.AppConfig;
import com.pedrogonic.trelloapiconsumer.model.parameter.SprintCalculatorServiceBoardInfo;
import com.pedrogonic.trelloapiconsumer.model.parameter.SprintCalculatorServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloCard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloChecklist;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SprintCalculatorService extends TrelloService{

    public void run(SprintCalculatorServiceRequestBody body) throws Exception {

        double totalPercent = body.getBoards().stream().map(board -> board.getSprintPercent()).reduce(0.0, (a,b) -> a + b);
        if (totalPercent != 100)
            throw  new Exception("Percentual não soma 100!");

        for (SprintCalculatorServiceBoardInfo boardInfo : body.getBoards())
            extractCards(boardInfo);
    }

    private void extractCards(SprintCalculatorServiceBoardInfo boardInfo) {
        try {

            TrelloList[] trelloLists = restTemplate.getForObject(
                    TRELLO_API_URL + "boards/" + boardInfo.getBoardId() + "/lists?token=" + AppConfig.API_TOKEN + "&key=" + AppConfig.API_KEY
                    , TrelloList[].class);

            TrelloList backlogList = Arrays.stream(trelloLists).filter(trelloList -> trelloList.getName().equalsIgnoreCase("Backlog")).collect(Collectors.toList()).get(0);

            TrelloCard[] trelloCards = restTemplate.getForObject(
                    TRELLO_API_URL + "lists/" + backlogList.getId() + "/cards/?cards=open&checklists=all&token=" + AppConfig.API_TOKEN + "&key=" + AppConfig.API_KEY
                    , TrelloCard[].class);

            for(TrelloCard card: trelloCards) {

                if (card.extractHoursFromName() == 0) {

                    for (TrelloChecklist trelloChecklist : card.getChecklistList()) {
                        card.setHours(trelloChecklist.getTrelloCheckItems().stream().map(
                                item -> item.getState().equals("incomplete") ? item.extractHoursFromName() : 0
                        ).reduce(0, (a, b) -> a + b));
                    }

                } else
                    card.setHours(card.extractHoursFromName());
                log.info(card.getName() + " - " + card.getHours());
            }



        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
