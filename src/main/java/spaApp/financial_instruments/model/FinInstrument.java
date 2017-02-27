package spaApp.financial_instruments.model;

import java.math.BigDecimal;

public class FinInstrument {

    Long id;
    String symbol;
    String yahooCode;
    BigDecimal spread;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getYahooCode() {
        return yahooCode;
    }

    public void setYahooCode(String yahooCode) {
        this.yahooCode = yahooCode;
    }

    public BigDecimal getSpread() {
        return spread;
    }

    public void setSpread(BigDecimal spread) {
        this.spread = spread;
    }
}
