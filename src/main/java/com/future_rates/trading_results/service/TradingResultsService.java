package com.future_rates.trading_results.service;

import com.future_rates.base_classifier.model.DataSet;
import com.future_rates.base_classifier.service.ClassifierService;
import com.future_rates.financial_instruments.repository.FinInstrumentRepository;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.service.RateService;
import com.future_rates.trading_results.model.TradingResults;
import com.future_rates.trading_results.repository.model.TradingResultsDb;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
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





    public TradingResults trade(String currency,Classifier classifier, int period, boolean dicretise){

        List<Rate> rates = rateService.getAllCurrencyRates(currency);
        Dataset learningSet = new DataSet(period).buildDataSet(rates);
        Dataset trainingSet = classifierService.getTrainigSet(learningSet);
        Dataset testSet = classifierService.getTestSet(learningSet);
        Dataset refferenceTestSet = classifierService.getTestSet(learningSet);
        if (dicretise) {
            classifierService.filterTestSetAndTrainingSet(testSet, trainingSet);
        }
        classifier.buildClassifier(trainingSet);
        Double profit = Double.valueOf(0);
        Double spread = finInstrumentRepository.getFinInstrumentBySymbol(currency).getSpread().doubleValue();

        for (int i=0; i< testSet.size()-1;i++){
            Object predictedClassValue = classifier.classify(testSet.get(i));
            Object realClassValue = testSet.get(i).classValue();
            Instance predictedInstance = refferenceTestSet.get(i+1);
            if (predictedClassValue.equals(realClassValue)) {
                double openCloseDifference = Math.abs(predictedInstance.get(predictedInstance.noAttributes()-4) -
                        predictedInstance.get(predictedInstance.noAttributes()-1));
                profit += openCloseDifference-spread;
            }else{
                double openCloseDifference = Math.abs(predictedInstance.get(predictedInstance.noAttributes()-4) -
                        predictedInstance.get(predictedInstance.noAttributes()-1));
                profit += -openCloseDifference-spread;

            }
        }

        TradingResults tradingResults = new TradingResults();
        tradingResults.setClassifier(String.valueOf(classifier.getClass()));
        tradingResults.setConceptInstanceSize(period);
        tradingResults.setFinancialInstrument(currency);
        tradingResults.setProfitPoints(BigDecimal.valueOf(profit));

        return tradingResults;
    }






    public static TradingResultsDb mapToTradingResultsDb(Long id, TradingResults api){
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

    public static TradingResults mapToTradingResults (TradingResultsDb db){
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
