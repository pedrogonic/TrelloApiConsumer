package com.pedrogonic.trelloapiconsumer.sprintProgress.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SprintProgressServiceResponseBody {

    private Double progress;
    private Double totalTasks;
    private Double doneTasks;
    private LocalDateTime dateTime;
    private List<SprintProgressServiceBoardProgress> boards = new ArrayList<>();

    public void addBoardProgress(SprintProgressServiceBoardProgress boardProgress){
        boards.add(boardProgress);
    }

}
