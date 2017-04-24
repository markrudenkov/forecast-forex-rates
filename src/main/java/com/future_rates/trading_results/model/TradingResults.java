package com.future_rates.trading_results.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;


public class TradingResults {

    private Long id;
    private String financialInstrument;
    private DateTime tradingStartDate;
    private DateTime tradingEndDate;
    private BigDecimal profitPoints;
    private BigDecimal profitUSD;
    private String classifier;
    private int conceptInstanceSize;
    private BigDecimal stopLossOrder;
    private BigDecimal takeProfitOrder;
    private BigDecimal TrailingStopLossOrder;
    private BigDecimal TrailingTakeProfitOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialInstrument() {
        return financialInstrument;
    }

    public void setFinancialInstrument(String financialInstrument) {
        this.financialInstrument = financialInstrument;
    }

    public DateTime getTradingStartDate() {
        return tradingStartDate;
    }

    public void setTradingStartDate(DateTime tradingStartDate) {
        this.tradingStartDate = tradingStartDate;
    }

    public DateTime getTradingEndDate() {
        return tradingEndDate;
    }

    public void setTradingEndDate(DateTime tradingEndDate) {
        this.tradingEndDate = tradingEndDate;
    }

    public BigDecimal getProfitPoints() {
        return profitPoints;
    }

    public void setProfitPoints(BigDecimal profitPoints) {
        this.profitPoints = profitPoints;
    }

    public BigDecimal getProfitUSD() {
        return profitUSD;
    }

    public void setProfitUSD(BigDecimal profitUSD) {
        this.profitUSD = profitUSD;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public int getConceptInstanceSize() {
        return conceptInstanceSize;
    }

    public void setConceptInstanceSize(int conceptInstanceSize) {
        this.conceptInstanceSize = conceptInstanceSize;
    }

    public BigDecimal getStopLossOrder() {
        return stopLossOrder;
    }

    public void setStopLossOrder(BigDecimal stopLossOrder) {
        this.stopLossOrder = stopLossOrder;
    }

    public BigDecimal getTakeProfitOrder() {
        return takeProfitOrder;
    }

    public void setTakeProfitOrder(BigDecimal takeProfitOrder) {
        this.takeProfitOrder = takeProfitOrder;
    }

    public BigDecimal getTrailingStopLossOrder() {
        return TrailingStopLossOrder;
    }

    public void setTrailingStopLossOrder(BigDecimal trailingStopLossOrder) {
        TrailingStopLossOrder = trailingStopLossOrder;
    }

    public BigDecimal getTrailingTakeProfitOrder() {
        return TrailingTakeProfitOrder;
    }

    public void setTrailingTakeProfitOrder(BigDecimal trailingTakeProfitOrder) {
        TrailingTakeProfitOrder = trailingTakeProfitOrder;
    }
}
