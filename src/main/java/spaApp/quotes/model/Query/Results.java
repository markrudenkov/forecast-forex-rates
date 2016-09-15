package spaApp.quotes.model.Query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {

    private List<Quote> quote;

    public Results() {
    }

    public List<Quote> getQuote() {
        return quote;
    }

    public void setQuote(List<Quote> quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Results{" +
                "quote=" + quote +
                '}';
    }
}
