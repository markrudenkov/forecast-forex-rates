package com.future_rates.rates.model.Instrument;

import org.joda.time.DateTime;

public class Instrument {
    private String symbol;
    private DateTime startDate;
    private DateTime endDate;
    private int atributes;
    private String method;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public int getAtributes() {
        return atributes;
    }

    public void setAtributes(int atributes) {
        this.atributes = atributes;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "selectedInstrument{" +
                "symbol='" + symbol + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
