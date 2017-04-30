package com.future_rates.rates.model.Query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

    private String count;
    private String created;
    private String lang;
    private Results results;

    public Query() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Query{" +
                "count='" + count + '\'' +
                ", created='" + created + '\'' +
                ", lang='" + lang + '\'' +
                ", trading_results=" + results +
                '}';
    }
}
