package com.pedrogonic.trelloapiconsumer.sprintProgress.model.response;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import lombok.Data;

@Data
public class SprintProgressServiceBoardProgress extends TrelloBoard {

    private Double progress;
    private Integer totalTasks;
    private Integer doneTasks;

}
