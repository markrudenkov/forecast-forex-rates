package quotes.model.Query;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String symbol;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjustedClose;

    public Quote(
            @JsonProperty("Symbol") String symbol,
            @JsonProperty("Open") String open,
            @JsonProperty("High") String high,
            @JsonProperty("Low") String low,
            @JsonProperty("Close") String close,
            @JsonProperty("Adj_Close") String adjustedClose) {
        this.symbol = symbol;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = adjustedClose;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "symbol='" + symbol + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", adjustedClose='" + adjustedClose + '\'' +
                '}';
    }
}
