package com.pedrogonic.trelloapiconsumer.model.trello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrelloItem {

    protected String id;
    protected String name;

}
