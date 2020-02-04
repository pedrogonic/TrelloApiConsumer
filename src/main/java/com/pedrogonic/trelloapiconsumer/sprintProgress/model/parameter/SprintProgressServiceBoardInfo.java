package com.pedrogonic.trelloapiconsumer.sprintProgress.model.parameter;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SprintProgressServiceBoardInfo extends TrelloBoard {

    private String sprintListId;
    private String doingListId;
    private String reviewListId;

}
