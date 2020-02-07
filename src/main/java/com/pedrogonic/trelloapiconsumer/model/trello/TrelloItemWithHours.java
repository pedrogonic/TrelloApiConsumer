package com.pedrogonic.trelloapiconsumer.model.trello;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class TrelloItemWithHours extends TrelloItem{

    protected Double hours;

    public Double extractHoursFromName() {
        int startIndex = this.name.lastIndexOf("-") + 1;
        int endIndex = this.name.lastIndexOf("h");
        Double hours = 0.0;
        try { hours = Double.parseDouble(this.name.substring(startIndex, endIndex).trim()); } catch (Exception e) {}
        return hours;
    }

}
