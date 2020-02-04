package com.pedrogonic.trelloapiconsumer.model.trello;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TrelloBoard extends TrelloItem {

    private String shortUrl;

    public TrelloBoard(String id, String name, String shortUrl) {
        super(id, name);
        this.shortUrl = shortUrl;
    }

}
