package com.pedrogonic.trelloapiconsumer.model.parameter;

import lombok.Data;

import java.util.List;


@Data
public class SprintCalculatorServiceRequestBody {

    private int sprintHours;
    private List<SprintCalculatorServiceBoardInfo> boards;

}