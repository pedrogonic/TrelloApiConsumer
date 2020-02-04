package com.pedrogonic.trelloapiconsumer.sprintProgress.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SprintProgressServiceResponseBody {

    private Double progress;
    private Integer totalTasks;
    private Integer doneTasks;
    private LocalDateTime dateTime;
    private List<SprintProgressServiceBoardProgress> boards;

}
