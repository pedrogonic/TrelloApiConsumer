package com.pedrogonic.trelloapiconsumer.sprintProgress.model.parameter;

import lombok.Data;

import java.util.List;

@Data
public class SprintProgressServiceRequestBody {

    private List<SprintProgressServiceBoardInfo> boards;

}
