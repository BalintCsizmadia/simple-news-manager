package com.newsmanager.web.dto;

import java.sql.Date;

public final class AmountOfDataPerDayDto {

    private final Date date;
    private final int amount;

    public AmountOfDataPerDayDto(Date date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }
}
