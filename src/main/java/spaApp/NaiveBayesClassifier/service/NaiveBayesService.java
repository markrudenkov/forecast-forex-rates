package spaApp.NaiveBayesClassifier.service;


import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.discretize.RecursiveMinimalEntropyPartitioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spaApp.NaiveBayesClassifier.model.DataSet;
import spaApp.NaiveBayesClassifier.model.Forecast;
import spaApp.NaiveBayesClassifier.model.RateInstance;

import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.Rate;
import spaApp.rates.service.RateService;

import java.util.List;

@Service
public class NaiveBayesService {

    @Autowired
    RateService rateService;

    @Autowired
    DataDiscretisationRMEP dataDiscretisationRMEP;

    public Dataset createDataSet(int atributes, String currency) {
        List<Rate> rates = rateService.getAllCurrencyRates(currency);
        DataSet dataset = new DataSet();
        dataset.setAtributes(atributes);
        return dataset.buildDataSet(dataset.getAtributes(), rates);

    }

    public Instance getLastUnclasifiedINstance(int atributes,String currency) {
        RateInstance rateInstance = new DataSet();
        rateInstance.setAtributes(atributes);
        List<Rate> rates= rateService.getLastCurrencyRates(atributes,currency );
        Instance unclasifiedInstance = rateInstance.createUnclasifiedInstance(atributes, rates);
        return  unclasifiedInstance;
    }

    public Object classification(int atributes,String currency){
        Dataset dicretizedDataSet = dataDiscretisationRMEP.dataSetDicretisation(atributes,currency);
        Instance instanceForPrediction = getLastUnclasifiedINstance(atributes,currency);
        dataDiscretisationRMEP.recursiveMinimalEntropyPartitioning.filter(instanceForPrediction);
        boolean useLaplace = true;
        boolean useLogs = true;
        Classifier naiveBayesClassifier = new NaiveBayesClassifier(useLaplace, useLogs, false);
        naiveBayesClassifier.buildClassifier(dicretizedDataSet);

        Object predictedClassValue =naiveBayesClassifier.classify(instanceForPrediction);
        return  predictedClassValue;
    }

    public Forecast getAnalysis (Instrument instrument){
        Forecast forecast = new Forecast();
         forecast.setForecastedBar(classification(4, instrument.getSymbol()).toString() );
         forecast.setSymbol(instrument.getSymbol());
        return forecast;
    }
}
