package com.pedrogonic.trelloapiconsumer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloChecklist extends TrelloItem  {

    @JsonAlias("checkItems")
    private List<TrelloCheckItem> trelloCheckItems;

}
