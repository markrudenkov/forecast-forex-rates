package com.future_rates.trading_results.model;


import net.sf.javaml.core.Instance;

public class ForecastedBar{
    private double open ;
    private double high ;
    private double low ;
    private double close;
    private double openCloseDifference;
    private double stopSide ;
    private double profitSide;
    private double stopLoss;
    private double takeProfit;

    public ForecastedBar(Instance predictedInstance, TradingResults tradingResults) {
        this.setOpen(predictedInstance.get(predictedInstance.noAttributes() - 4));
        this.setHigh(predictedInstance.get(predictedInstance.noAttributes() - 3));
        this.setLow(predictedInstance.get(predictedInstance.noAttributes() - 2));
        this.setClose(predictedInstance.get(predictedInstance.noAttributes() - 1));
        this.setOpenCloseDifference(Math.abs(this.getOpen() - this.getClose()));
        this.setStopSide(getStopSide());
        this.setProfitSide(getProfitSide());
        this.setStopLoss(tradingResults.getStopLossOrder().doubleValue());
        this.setTakeProfit(tradingResults.getTakeProfitOrder().doubleValue());
        if(getOpen() - getClose() > 0){
            //white bar
            setStopSide(Math.abs(getOpen() - getLow()));
            setProfitSide(Math.abs(getHigh() - getOpen()));
        }else{
            // black bar
            setStopSide(Math.abs(getHigh() - getOpen()));
            setProfitSide(Math.abs(getOpen() - getLow()));
        }
    }


    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpenCloseDifference() {
        return openCloseDifference;
    }

    public void setOpenCloseDifference(double openCloseDifference) {
        this.openCloseDifference = openCloseDifference;
    }

    public double getStopSide() {
        return stopSide;
    }

    public void setStopSide(double stopSide) {
        this.stopSide = stopSide;
    }

    public double getProfitSide() {
        return profitSide;
    }

    public void setProfitSide(double profitSide) {
        this.profitSide = profitSide;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }
}

