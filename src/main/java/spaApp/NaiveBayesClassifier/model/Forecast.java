package spaApp.NaiveBayesClassifier.model;


public class Forecast {

    String forecastedBar;
    String symbol;

    public String getForecastedBar() {
        return forecastedBar;
    }

    public void setForecastedBar(String forecastedBar) {
        this.forecastedBar = forecastedBar;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
