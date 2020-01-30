package com.pedrogonic.trelloapiconsumer.model.trello;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloList {

    private String id;
    private String name;

}
