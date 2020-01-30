package com.pedrogonic.trelloapiconsumer.model.trello;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCard extends TrelloItemWithHours {

    private Boolean closed;
    @JsonAlias("checklists")
    private List<TrelloChecklist> checklistList;



}
