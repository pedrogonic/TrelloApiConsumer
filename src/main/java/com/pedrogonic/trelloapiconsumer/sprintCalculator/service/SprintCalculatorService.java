package com.pedrogonic.trelloapiconsumer.sprintCalculator.service;

import com.pedrogonic.trelloapiconsumer.sprintCalculator.model.parameter.SprintCalculatorServiceBoardInfo;
import com.pedrogonic.trelloapiconsumer.sprintCalculator.model.parameter.SprintCalculatorServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloCard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloChecklist;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloList;
import com.pedrogonic.trelloapiconsumer.service.TrelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SprintCalculatorService extends TrelloService {

    double sprintHours;
    Map<SprintCalculatorServiceBoardInfo, List<TrelloCard>> cards;
    Map<SprintCalculatorServiceBoardInfo, List<TrelloCard>> leftoverCards;

    public double run(SprintCalculatorServiceRequestBody body) throws Exception {

        log.info("Rodando serviço de cálculo de sprint!");

        double totalPercent = body.getBoards().stream().map(board -> board.getSprintPercent()).reduce(0.0, (a,b) -> a + b);
        if (totalPercent != 100)
            throw  new Exception("Percentual não soma 100!");

        cards = new HashMap<>();
        leftoverCards = new HashMap<>();

        sprintHours = body.getSprintHours();

        double leftoverHours = sprintHours;

        for (SprintCalculatorServiceBoardInfo boardInfo : body.getBoards()) {  processBoard(boardInfo);  }

        for(Map.Entry<SprintCalculatorServiceBoardInfo, List<TrelloCard>> entry : cards.entrySet()) {
            for (TrelloCard card : entry.getValue()) {
                leftoverHours -= card.getHours();
                log.info(entry.getKey().getId() + ": " + card.getName() + " - " + card.getHours());
                moveCardToSprint(entry.getKey(), card);
            }
        }

        List<TrelloCard> l = new ArrayList<>();
        cards.entrySet().forEach(entry -> l.addAll(entry.getValue()));

        for (TrelloCard card : l) {
            if (leftoverHours - card.getHours() >= 0) {
                leftoverHours -= card.getHours();

                SprintCalculatorServiceBoardInfo board = (SprintCalculatorServiceBoardInfo) cards.entrySet().stream().filter(
                        entry -> entry.getValue().contains(card)
                    ).map(Map.Entry::getKey);

                moveCardToSprint(board, card);

                cards.get(board).add(card);
                leftoverCards.get(board).remove(card);

            }
        }

        log.info("Cards da sprint: ");
        cards.entrySet().stream().forEach(entry -> entry.getValue().stream().forEach(c -> log.info(entry.getKey().getId() + "- " + c.getName())));

        log.info("Cards restantes no backlog: ");
        leftoverCards.entrySet().stream().forEach(entry -> entry.getValue().stream().forEach(c -> log.info(entry.getKey().getId() + "- " + c.getName())));

        log.info("Horas restantes: " + leftoverHours);
        return leftoverHours;
    }

    private void moveCardToSprint(SprintCalculatorServiceBoardInfo boardInfo, TrelloCard card) {

        String url = TRELLO_API_URL + "card/" + card.getId() + "/?idList=" + boardInfo.getSprintListId() + "&" + API_AND_TOKEN_PARAMS;
        restTemplate.put( url, null );

    }

    private void processBoard(SprintCalculatorServiceBoardInfo boardInfo) {
        double projectHours = sprintHours * boardInfo.getSprintPercent() / 100;

        boardInfo.setId(getBoardIdBoardInfo(boardInfo));
        boardInfo.setBacklogListId(getBoardListIdByName(boardInfo, "Backlog"));
        boardInfo.setSprintListId(getBoardListIdByName(boardInfo, "Sprint"));

        List<TrelloCard> boardCards = extractCards(boardInfo);
        List<TrelloCard> sprintCards = new ArrayList<>();
        for (int i = 0; i < boardCards.size() && projectHours > 0; i++ ) {
            if (projectHours >= boardCards.get(i).getHours()) {
                projectHours -= boardCards.get(i).getHours();
                sprintCards.add(boardCards.get(i));
            }
        }

        boardCards.removeAll(sprintCards);
        cards.put(boardInfo, sprintCards);
        leftoverCards.put(boardInfo, boardCards);
    }

    // TODO pull up
    private String getBoardIdBoardInfo(SprintCalculatorServiceBoardInfo boardInfo) {
        return restTemplate.getForObject(TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/?" + API_AND_TOKEN_PARAMS
                , TrelloBoard.class).getId();
    }

    private String getBoardListIdByName(SprintCalculatorServiceBoardInfo boardInfo, String listName) {
        TrelloList[] trelloLists = restTemplate.getForObject(
                TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/lists?" + API_AND_TOKEN_PARAMS
                , TrelloList[].class);

        TrelloList trelloList = Arrays.stream(trelloLists).filter(tl -> tl.getName().equalsIgnoreCase(listName)).collect(Collectors.toList()).get(0);

        return trelloList.getId();
    }

    private List<TrelloCard> extractCards(SprintCalculatorServiceBoardInfo boardInfo) {
        try {

            List<TrelloCard> trelloCards = new ArrayList<>(Arrays.asList(
                    restTemplate.getForObject(
                    TRELLO_API_URL + "lists/" + boardInfo.getBacklogListId() + "/cards/?cards=open&checklists=all&" + API_AND_TOKEN_PARAMS
                    , TrelloCard[].class)
                ));

            for(TrelloCard card: trelloCards) {

                if (card.extractHoursFromName() == 0) {

                    for (TrelloChecklist trelloChecklist : card.getChecklistList()) {
                        card.setHours(trelloChecklist.getTrelloCheckItems().stream().map(
                                item -> item.getState().equals("incomplete") ? item.extractHoursFromName() : 0
                        ).reduce(0.0, Double::sum));
                    }

                } else
                    card.setHours(card.extractHoursFromName());
                log.info(card.getId() + " - " + card.getName() + " - " + card.getHours());
            }

            return trelloCards;

        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

}
