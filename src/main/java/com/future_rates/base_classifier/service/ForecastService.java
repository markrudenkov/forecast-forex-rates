package com.future_rates.base_classifier.service;

import com.future_rates.base_classifier.model.Analysis;
import com.future_rates.base_classifier.model.RateInstance;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.service.RateService;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastService extends ClassifierService {

    @Autowired
    RateService rateService;
    DefaultDataset learningSet;
    DenseInstance instanceForPrediction;
    Classifier clf;

    private Instance getLastUnclasifiedINstance(int atributes, String currency) {
        RateInstance rateInstance = new RateInstance(atributes);
        List<Rate> rates = rateService.getLastCurrencyRates(atributes, currency);
        Instance unclasifiedInstance = rateInstance.createUnclasifiedInstance(rates);
        return unclasifiedInstance;
    }

    private ForecastService getDataForClassification(int atributes, String currency){
        this.learningSet = (DefaultDataset) createDataSet(atributes, currency);
        this.instanceForPrediction = (DenseInstance) getLastUnclasifiedINstance(atributes, currency);
        filterDatasetAndInstance(learningSet, instanceForPrediction); //
        return this;
    }


    public Object classification() {
        this.clf.buildClassifier(learningSet);
        Object predictedClassValue = this.clf.classify(this.instanceForPrediction);
        return predictedClassValue;
    }

    public Analysis getForecast(Instrument instrument) {
        Analysis forecast = new Analysis();
        forecast.setForecastedBar(classification(4, instrument.getSymbol()).toString());
        forecast.setSymbol(instrument.getSymbol());
        return forecast;
    }
}

