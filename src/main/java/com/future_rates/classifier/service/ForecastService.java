package com.future_rates.classifier.service;

import com.future_rates.classifier.model.RateInstance;
import com.future_rates.classifiers_wrapper.model.ClassifiersWrapper;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.service.RateService;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
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

    private ForecastService getLastUnclasifiedINstance(Instrument instrument) {
        List<Rate> instanceRates = rateService.getLastCurrencyRates(instrument.getInstancePeriod(), instrument.getSymbol());
        this.instanceForPrediction = (DenseInstance) new RateInstance(instrument.getInstancePeriod()).createUnclasifiedInstance(instanceRates);
        return this;
    }

    private ForecastService getDataForClassification(Instrument instrument) {
        this.learningSet = (DefaultDataset) createDataSet(instrument.getInstancePeriod(), instrument.getSymbol());
        return this;
    }

    public Object classification(Instrument instrument) {
        this.clf = (Classifier) selectClassifier(instrument.getClassifierName());
        this.clf.buildClassifier(learningSet);
        return this.clf.classify(this.instanceForPrediction);
    }

    private ForecastService discretiseData(Instrument instrument) {
        if (instrument.getClassifierName() == "Naive Bayes") {
            filterDatasetAndInstance(this.learningSet, instanceForPrediction);
        }
        return this;
    }

//    private Object selectClassifier(String classifierName) {
//        ClassifiersWrapper clfWrapper = new ClassifiersWrapper();
//        Object classifier = new Object();
//        switch (classifierName) {
//            case "Naive Bayes":
//                classifier = clfWrapper.getBayes();
//            case "Support Vector Machines":
//                classifier = clfWrapper.getSvm();
//            case "Random Forest":
//                classifier = clfWrapper.getRndForest();
//            case "svmSelfOpt":
//                classifier = clfWrapper.getScmSelfOpt();
//        }
//        return classifier;
//    }


    public Instrument getForecast(Instrument api) {
        api.setForecastedBar(
                (String) getLastUnclasifiedINstance(api).
                        getDataForClassification(api).discretiseData(api).classification(api)
        );
        return api;
    }
}

