package spaApp.quotes.model.Query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mark on 16.9.8.
 */
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
