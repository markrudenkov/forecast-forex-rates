package spaApp.classifier.service;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.discretize.RecursiveMinimalEntropyPartitioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spaApp.classifier.model.*;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.Rate;
import spaApp.rates.service.RateService;

import java.util.List;

@Service
public class NaiveBayesEfficiencyService {

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

    public Instance getLastUnclasifiedINstance(int atributes, String currency) {
        RateInstance rateInstance = new DataSet();
        rateInstance.setAtributes(atributes);
        List<Rate> rates= rateService.getLastCurrencyRates(atributes,currency );
        Instance unclasifiedInstance = rateInstance.createUnclasifiedInstance(atributes, rates);
        return  unclasifiedInstance;
    }



    public double getAccuracy(int atributes,String currency){
        List<Rate> rates = rateService.getAllCurrencyRates(currency);
        Dataset trainingSet = new TrainingDataSet().buildTrainingDataSet(atributes,rates);
        Dataset testSet = new TestDataSet().buildTestDataSet(atributes,rates);
        boolean sparse = false;
        RecursiveMinimalEntropyPartitioning rmep = new RecursiveMinimalEntropyPartitioning(sparse);
        rmep.build(trainingSet);
        rmep.filter(trainingSet);
        rmep.filter(testSet);

        boolean useLaplace = true;
        boolean useLogs = true;
        Classifier naiveBayesClassifier = new NaiveBayesClassifier(useLaplace, useLogs, false);
        naiveBayesClassifier.buildClassifier(trainingSet);
        int correct=0;
        int wrong = 0;

        for(Instance instance : testSet){

            Object predictedClassValue =naiveBayesClassifier.classify(instance);
            Object realClassValue = instance.classValue();
            if (predictedClassValue.equals(realClassValue))
                correct++;
            else
                wrong++;

        }

        double accuracy=correct*100/(correct+wrong);

        return  accuracy;
    }

    public Forecast getAnalysis (Instrument instrument){
        Forecast forecast = new Forecast();
        forecast.setAccuraccy(String.valueOf(getAccuracy(4, instrument.getSymbol().toString() )));
        forecast.setSymbol(instrument.getSymbol());
        return forecast;
    }

}
