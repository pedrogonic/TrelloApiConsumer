package com.pedrogonic.trelloapiconsumer.prepareBoardForScrum.model.parameter;

import lombok.Data;

import java.util.List;

@Data
public class PrepareBoardForScrumServiceRequestBody {

    private List<PrepareBoardForScrumServiceBoardInfo> boards;

}
