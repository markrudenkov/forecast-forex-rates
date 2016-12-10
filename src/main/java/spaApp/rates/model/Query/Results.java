package spaApp.rates.model.Query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {

    private List<Rate> rate;

    public Results() {
    }

    public List<Rate> getRate() {
        return rate;
    }

    public void setRate(List<Rate> rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Results{" +
                "rate=" + rate +
                '}';
    }
}
