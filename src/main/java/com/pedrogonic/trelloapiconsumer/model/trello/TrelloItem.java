package com.pedrogonic.trelloapiconsumer.model.trello;

import lombok.Data;

@Data
public abstract class TrelloItem {

    protected String id;
    protected String name;

}
