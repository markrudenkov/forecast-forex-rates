package com.future_rates.trading_results.service;

import com.future_rates.base_classifier.model.DataSet;
import com.future_rates.base_classifier.service.ClassifierService;
import com.future_rates.financial_instruments.repository.FinInstrumentRepository;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.service.RateService;
import com.future_rates.trading_results.model.ForecastedBar;
import com.future_rates.trading_results.model.TradingResults;
import com.future_rates.trading_results.repository.model.TradingResultsDb;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TradingResultsService {

    @Autowired
    RateService rateService;

    @Autowired
    ClassifierService classifierService;

    @Autowired
    FinInstrumentRepository finInstrumentRepository;

    DefaultDataset testSet;
    DefaultDataset refferenceTestSet;
    Classifier classifier;
    Double profit;
    Double spread;
    boolean dicretise=true;

    private TradingResultsService getDataForAnalysis(TradingResults tradingParameters) {
        List<Rate> rates = rateService.getAllCurrencyRates(tradingParameters.getFinancialInstrument());
        Dataset learningSet = new DataSet(tradingParameters.getConceptInstanceSize()).buildDataSet(rates);
        Dataset trainingSet = classifierService.getTrainigSet(learningSet);
        this.testSet = (DefaultDataset) classifierService.getDefaultTestSet(learningSet).copy();
        this.refferenceTestSet = (DefaultDataset)classifierService.getTestSet(learningSet);
        this.classifier = new NaiveBayesClassifier(true, true, false);;
        if (this.dicretise) {
            classifierService.filterTestSetAndTrainingSet(testSet, trainingSet);
        }
        this.classifier.buildClassifier(trainingSet);
        this.profit = Double.valueOf(0);
        this.spread = finInstrumentRepository.getFinInstrumentBySymbol(
                tradingParameters.getFinancialInstrument()).getSpread().doubleValue();
        return this;
    }

    private TradingResultsService getProfitWithOrders(TradingResults tradingResults) {
        for (int i = 0; i < this.testSet.size() - 1; i++) {
            Object predictedClassValue = this.classifier.classify(this.testSet.get(i));
            Object realClassValue = this.testSet.get(i).classValue();
            Instance predictedInstance = this.refferenceTestSet.get(i + 1);
            ForecastedBar fb = new ForecastedBar(predictedInstance, tradingResults);

            if (predictedClassValue.equals(realClassValue)) {
                // correct prediction
                if(fb.getStopSide() > fb.getStopLoss()){
                    this.profit += -fb.getStopLoss() - this.spread;
                }else if((fb.getProfitSide() > fb.getTakeProfit()) && (fb.getTakeProfit() != 0)){
                    this.profit += fb.getTakeProfit() - this.spread;
                }else {
                    this.profit += fb.getOpenCloseDifference() - this.spread;
                }
            } else {
                // incorrect prediction
                if(fb.getProfitSide() > fb.getStopLoss()){
                    this.profit += -fb.getStopLoss() - this.spread;
                }else {
                    this.profit += -fb.getOpenCloseDifference() - this.spread;
                }
            }
        }
        return this;
    }

    private TradingResults updateTradingResults(TradingResults tradingResults) {
        tradingResults.setProfitPoints(BigDecimal.valueOf(this.profit));
        return tradingResults;
    }

    public TradingResults tradeWithOrders(TradingResults tradingResults) {
        return getDataForAnalysis(tradingResults).
                getProfitWithOrders(tradingResults).updateTradingResults(tradingResults);
    }

    private TradingResultsService getProfitWithoutStopOrders() {
        for (int i = 0; i < testSet.size() - 1; i++) {
            Object predictedClassValue = this.classifier.classify(testSet.get(i));
            Object realClassValue = testSet.get(i).classValue();
            Instance predictedInstance = refferenceTestSet.get(i + 1);
            if (predictedClassValue.equals(realClassValue)) {
                double openCloseDifference = Math.abs(predictedInstance.get(predictedInstance.noAttributes() - 4) -
                        predictedInstance.get(predictedInstance.noAttributes() - 1));
                this.profit += openCloseDifference - spread;
            } else {
                double openCloseDifference = Math.abs(predictedInstance.get(predictedInstance.noAttributes() - 4) -
                        predictedInstance.get(predictedInstance.noAttributes() - 1));
                this.profit += -openCloseDifference - spread;
            }
        }
        return this;
    }

    public static TradingResultsDb mapToTradingResultsDb(Long id, TradingResults api) {
        TradingResultsDb db = new TradingResultsDb();
        db.setId(api.getId());
        db.setFinancialInstrument(api.getFinancialInstrument());
        db.setProfitPoints(api.getProfitPoints());
        db.setProfitUSD(api.getProfitUSD());
        db.setClassifier(api.getClassifier());
        db.setConceptInstanceSize(api.getConceptInstanceSize());
        db.setStopLossOrder(api.getStopLossOrder());
        db.setTakeProfitOrder(api.getTakeProfitOrder());
        db.setTrailingStopLossOrder(api.getTrailingStopLossOrder());
        db.setTrailingTakeProfitOrder(api.getTrailingTakeProfitOrder());
        return db;
    }

    public static TradingResults mapToTradingResults(TradingResultsDb db) {
        TradingResults api = new TradingResults();
        api.setId(db.getId());
        api.setFinancialInstrument(db.getFinancialInstrument());
        api.setProfitPoints(api.getProfitPoints());
        api.setProfitUSD(api.getProfitUSD());
        api.setClassifier(api.getClassifier());
        api.setConceptInstanceSize(api.getConceptInstanceSize());
        api.setStopLossOrder(api.getStopLossOrder());
        api.setTakeProfitOrder(api.getTakeProfitOrder());
        api.setTrailingStopLossOrder(api.getTrailingStopLossOrder());
        api.setTrailingTakeProfitOrder(api.getTrailingTakeProfitOrder());
        return api;
    }



}
