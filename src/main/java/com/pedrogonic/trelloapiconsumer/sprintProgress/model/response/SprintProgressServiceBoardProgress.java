package com.pedrogonic.trelloapiconsumer.sprintProgress.model.response;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SprintProgressServiceBoardProgress extends TrelloBoard {

    private Double progress = 0.0;
    private Double totalTasks = 0.0;
    private Double doneTasks = 0.0;

    public SprintProgressServiceBoardProgress(TrelloBoard trelloBoard) {
        super(trelloBoard.getId(), trelloBoard.getName(), trelloBoard.getShortUrl());
    }

    public Double addTotalTasks(Double tasks) {
        totalTasks += tasks;
        return  totalTasks;
    }

    public Double addDoneTasks(Double tasks) {
        doneTasks += tasks;
        return  doneTasks;
    }

}
