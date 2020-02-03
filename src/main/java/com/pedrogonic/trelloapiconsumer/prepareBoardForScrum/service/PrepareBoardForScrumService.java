package com.pedrogonic.trelloapiconsumer.prepareBoardForScrum.service;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import com.pedrogonic.trelloapiconsumer.model.trello.TrelloList;
import com.pedrogonic.trelloapiconsumer.prepareBoardForScrum.model.parameter.PrepareBoardForScrumServiceBoardInfo;
import com.pedrogonic.trelloapiconsumer.prepareBoardForScrum.model.parameter.PrepareBoardForScrumServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.service.TrelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrepareBoardForScrumService extends TrelloService {

    List<TrelloList> trelloLists;
    final List<String> SCRUM_LISTS_NAMES = Arrays.asList("Backlog", "Sprint", "Doing", "Review", "Done");

    public void run(PrepareBoardForScrumServiceRequestBody body) {

        log.info("Rodando serviço que prepara os boards para o processo de SCRUM!");

        for (PrepareBoardForScrumServiceBoardInfo boardInfo : body.getBoards()) {

            log.info("Preparando o board de url " + boardInfo.getShortUrl());

            TrelloBoard board = getBoardFromBoardInfo(boardInfo);

            log.info("=== Board " + board.getName());

            trelloLists = getBoardLists(boardInfo);

            createScrumLists(board);

        }

    }

    // TODO pull up
    private TrelloBoard getBoardFromBoardInfo(PrepareBoardForScrumServiceBoardInfo boardInfo) {
        return restTemplate.getForObject(TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/?" + API_AND_TOKEN_PARAMS
                , TrelloBoard.class);
    }

    private List<TrelloList> getBoardLists(PrepareBoardForScrumServiceBoardInfo boardInfo) {
        TrelloList[] trelloLists = restTemplate.getForObject(
                TRELLO_API_URL + "boards/" + boardInfo.getShortUrl() + "/lists?" + API_AND_TOKEN_PARAMS
                , TrelloList[].class);

        return new ArrayList<>( Arrays.asList(trelloLists));
    }

    private boolean existsListWithName(String name) {
        return trelloLists.stream().filter(tl -> tl.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).size() > 0;
    }

    private void createScrumLists(TrelloBoard board) {

        for (String listName : SCRUM_LISTS_NAMES) {

            if ( !existsListWithName(listName)) {
                log.info("Criando lista " + listName + ".");
                createList(board, listName);

            }

        }

    }

    private void createList(TrelloBoard board, String  listName) {
        String url = TRELLO_API_URL + "lists?idBoard=" + board.getId() + "&name=" + listName + "&" + API_AND_TOKEN_PARAMS;
        restTemplate.postForEntity( url, null, null );
    }
}
