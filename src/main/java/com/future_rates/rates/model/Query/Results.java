package com.future_rates.rates.model.Query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {


    private List<Rate> quote;

    public Results() {
    }

    public List<Rate> getQuote() {
        return quote;
    }

    public void setQuote(List<Rate> quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Results{" +
                "quote=" + quote +
                '}';
    }
}
