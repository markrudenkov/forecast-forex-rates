package com.future_rates.rates.model.Query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryWrapper {

    private Query query;

    public QueryWrapper() {
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "QueryWrapper{" +
                "query=" + query +
                '}';
    }
}
