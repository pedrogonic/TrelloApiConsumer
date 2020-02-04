package com.pedrogonic.trelloapiconsumer.sprintProgress.model.response;

import com.pedrogonic.trelloapiconsumer.model.trello.TrelloBoard;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SprintProgressServiceBoardProgress extends TrelloBoard {

    private Double progress = 0.0;
    private Integer totalTasks = 0;
    private Integer doneTasks = 0;

    public SprintProgressServiceBoardProgress(TrelloBoard trelloBoard) {
        super(trelloBoard.getId(), trelloBoard.getName(), trelloBoard.getShortUrl());
    }

    public Integer addTotalTasks(Integer tasks) {
        totalTasks += tasks;
        return  totalTasks;
    }

    public Integer addDoneTasks(Integer tasks) {
        doneTasks += tasks;
        return  doneTasks;
    }

}
