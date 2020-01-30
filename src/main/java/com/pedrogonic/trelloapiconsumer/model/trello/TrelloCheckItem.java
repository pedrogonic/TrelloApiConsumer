package com.pedrogonic.trelloapiconsumer.model.trello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCheckItem extends TrelloItemWithHours  {

    private String state;

}
