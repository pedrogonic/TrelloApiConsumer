package com.pedrogonic.trelloapiconsumer.sprintProgress.service;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloCard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloChecklist;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloList;
import com.pedrogonic.trelloapiconsumer.service.TrelloService;
import com.pedrogonic.trelloapiconsumer.sprintProgress.model.parameter.SprintProgressServiceBoardInfo;
import com.pedrogonic.trelloapiconsumer.sprintProgress.model.parameter.SprintProgressServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.sprintProgress.model.response.SprintProgressServiceBoardProgress;
import com.pedrogonic.trelloapiconsumer.sprintProgress.model.response.SprintProgressServiceResponseBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SprintProgressService extends TrelloService {


    public SprintProgressServiceResponseBody run(SprintProgressServiceRequestBody body) {

        log.info("Rodando serviço de cálculo do progresso da sprint!");

        SprintProgressServiceResponseBody serviceResponseBody = new SprintProgressServiceResponseBody();

        for (SprintProgressServiceBoardInfo boardInfo : body.getBoards()) {
            serviceResponseBody.addBoardProgress(processBoard(boardInfo));
        }

        serviceResponseBody.setDateTime(LocalDateTime.now());
        serviceResponseBody.setTotalTasks( serviceResponseBody.getBoards().stream().map(
                board -> board.getTotalTasks()).reduce(0,Integer::sum)
        );
        serviceResponseBody.setDoneTasks( serviceResponseBody.getBoards().stream().map(
                board -> board.getDoneTasks()).reduce(0,Integer::sum)
        );
        serviceResponseBody.setProgress( serviceResponseBody.getDoneTasks() * 1.0 / serviceResponseBody.getTotalTasks() );

        return serviceResponseBody;
    }

    private SprintProgressServiceBoardProgress processBoard(SprintProgressServiceBoardInfo boardInfo) {

        SprintProgressServiceBoardProgress boardProgress = new SprintProgressServiceBoardProgress(getBoardIdBoardInfo(boardInfo));

        boardInfo.setSprintListId(getBoardListIdByName(boardInfo, "Sprint"));
        boardInfo.setDoingListId(getBoardListIdByName(boardInfo, "Doing"));
        boardInfo.setReviewListId(getBoardListIdByName(boardInfo, "Review"));

        List<TrelloCard> openCards = extractCards(boardInfo.getSprintListId());
        openCards.addAll(extractCards(boardInfo.getDoingListId()));
        List<TrelloCard> closedCards = extractCards(boardInfo.getReviewListId());

        processCards(boardProgress, openCards, "Open");
        processCards(boardProgress, closedCards, "Closed");

        boardProgress.setProgress( boardProgress.getDoneTasks() * 1.0 / boardProgress.getTotalTasks() );

        return boardProgress;
    }

    private TrelloBoard getBoardIdBoardInfo(SprintProgressServiceBoardInfo boardInfo) {
        return restTemplate.getForObject(TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/?" + API_AND_TOKEN_PARAMS
                , TrelloBoard.class);
    }

    private String getBoardListIdByName(SprintProgressServiceBoardInfo boardInfo, String listName) {
        TrelloList[] trelloLists = restTemplate.getForObject(
                TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/lists?" + API_AND_TOKEN_PARAMS
                , TrelloList[].class);

        TrelloList trelloList = Arrays.stream(trelloLists).filter(tl -> tl.getName().equalsIgnoreCase(listName)).collect(Collectors.toList()).get(0);

        return trelloList.getId();
    }

    private List<TrelloCard> extractCards(String listId) {
        try {

            return new ArrayList<>(Arrays.asList(
                    restTemplate.getForObject(
                            TRELLO_API_URL + "lists/" + listId + "/cards/?cards=open&checklists=all&" + API_AND_TOKEN_PARAMS
                            , TrelloCard[].class)
            ));

        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    private void processCards(SprintProgressServiceBoardProgress boardProgress, List<TrelloCard> cards, String list) {

        for(TrelloCard card: cards) {

            if (card.extractHoursFromName() == 0) {

                for (TrelloChecklist trelloChecklist : card.getChecklistList()) {
                    trelloChecklist.getTrelloCheckItems().stream().forEach(
                            item -> {
                                Integer hours = item.extractHoursFromName();
                                if(!(item.getState().equals("incomplete") && list.equalsIgnoreCase("Open"))) boardProgress.addDoneTasks(hours);
                                boardProgress.addTotalTasks(hours);
                            }
                    );
                }

            } else {
                Integer hours = card.extractHoursFromName();
                if (list.equalsIgnoreCase("Closed"))
                    boardProgress.addDoneTasks(hours);
                boardProgress.addTotalTasks(hours);
            }
        }

    }

}
