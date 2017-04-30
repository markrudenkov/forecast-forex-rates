package com.future_rates.naive_bayes_classifier.service;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.future_rates.base_classifier.model.*;
import com.future_rates.base_classifier.service.ClassifierService;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.service.RateService;

@Service
public class NaiveBayesPerformanceService extends ClassifierService {

    @Autowired
    RateService rateService;

    Classifier naiveBayesClassifier = new NaiveBayesClassifier(true, true, false);

    public Analysis getAnalysis(Instrument instrument) {
        Analysis forecast = new Analysis();
        forecast.setAccuraccy(String.valueOf(getAveragePerformance(4, instrument.getSymbol().toString(), naiveBayesClassifier, true)));
        forecast.setSymbol(instrument.getSymbol());
        return forecast;
    }

}
