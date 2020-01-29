package com.pedrogonic.trelloapiconsumer.model;

import lombok.Data;

@Data
public abstract class TrelloItemWithHours extends TrelloItem{

    protected Integer hours;

    public Integer extractHoursFromName() {
        int startIndex = this.name.lastIndexOf("-") + 1;
        int endIndex = this.name.lastIndexOf("h");
        Integer hours = 0;
        try { hours = Integer.parseInt(this.name.substring(startIndex, endIndex).trim()); } catch (Exception e) {}
        return hours;
    }

}
