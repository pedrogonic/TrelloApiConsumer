package com.pedrogonic.trelloapiconsumer.sprintPlanning.model;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import lombok.Data;

@Data
public class SprintPlanningResultServiceBoardInfo extends TrelloBoard {

    private String sprintListId;

}
