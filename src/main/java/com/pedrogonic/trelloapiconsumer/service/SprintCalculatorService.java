package com.pedrogonic.trelloapiconsumer.service;

import com.pedrogonic.trelloapiconsumer.config.AppConfig;
import com.pedrogonic.trelloapiconsumer.model.TrelloCard;
import com.pedrogonic.trelloapiconsumer.model.TrelloChecklist;
import com.pedrogonic.trelloapiconsumer.model.TrelloList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SprintCalculatorService extends TrelloService{

    // TODO: pass this by args
    List<String> boardsIds = Arrays.asList("BOoXLhUs", "BJDCfhvw");

    @Override
    public void run(String[] args) {

        for (String boardId : boardsIds)
            extractCards(boardId);
    }

    private void extractCards(String boardId) {
        try {

            TrelloList[] trelloLists = restTemplate.getForObject(
                    TRELLO_API_URL + "boards/" + boardId + "/lists?token=" + AppConfig.API_TOKEN + "&key=" + AppConfig.API_KEY
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
