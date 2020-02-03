package com.pedrogonic.trelloapiconsumer.sprintCalculator.model.parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SprintCalculatorServiceBoardInfo {

    @JsonIgnore
    private String id;

    private String shortUrl;
    private double sprintPercent;
    private String backlogListId;
    private String sprintListId;

}
