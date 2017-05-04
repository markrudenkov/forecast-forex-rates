package com.future_rates.rates.model.Instrument;

import org.joda.time.DateTime;

public class Instrument {
    private String symbol;
    private DateTime startDate;
    private DateTime endDate;
    private int instancePeriod;
    private String classifierName;
    private String accuraccy;
    private String forecastedBar;

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


    public int getInstancePeriod() {
        return instancePeriod;
    }

    public void setInstancePeriod(int instancePeriod) {
        this.instancePeriod = instancePeriod;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    public String getAccuraccy() {
        return accuraccy;
    }

    public void setAccuraccy(String accuraccy) {
        this.accuraccy = accuraccy;
    }

    public String getForecastedBar() {
        return forecastedBar;
    }

    public void setForecastedBar(String forecastedBar) {
        this.forecastedBar = forecastedBar;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "symbol='" + symbol + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", instancePeriod=" + instancePeriod +
                ", classifierName='" + classifierName + '\'' +
                ", accuraccy='" + accuraccy + '\'' +
                ", forecastedBar='" + forecastedBar + '\'' +
                '}';
    }
}
