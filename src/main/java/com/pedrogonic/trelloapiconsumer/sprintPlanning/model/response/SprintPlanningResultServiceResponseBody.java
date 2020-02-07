package com.pedrogonic.trelloapiconsumer.sprintPlanning.model.response;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloCard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SprintPlanningResultServiceResponseBody {

    List<TrelloCard> cards;

}
