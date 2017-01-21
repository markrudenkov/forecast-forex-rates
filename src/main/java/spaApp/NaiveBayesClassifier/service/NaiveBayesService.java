package spaApp.NaiveBayesClassifier.service;


import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spaApp.Classifier.model.Analysis;
import spaApp.Classifier.model.RateInstance;
import spaApp.Classifier.service.ClassifierService;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.Rate;
import spaApp.rates.service.RateService;
import java.util.List;

@Service
public class NaiveBayesService extends ClassifierService {

    @Autowired
    RateService rateService;

    public Instance getLastUnclasifiedINstance(int atributes,String currency) {
        RateInstance rateInstance = new RateInstance(atributes);
        List<Rate> rates= rateService.getLastCurrencyRates(atributes,currency );
        Instance unclasifiedInstance = rateInstance.createUnclasifiedInstance(rates);
        return  unclasifiedInstance;
    }

    public Object classification(int atributes,String currency){
        Dataset learningSet = createDataSet(atributes,currency);
        Instance instanceForPrediction = getLastUnclasifiedINstance(atributes,currency);
        filterDatasetAndInstance(learningSet,instanceForPrediction);

        Classifier naiveBayesClassifier = new NaiveBayesClassifier(true, true, false);
        naiveBayesClassifier.buildClassifier(learningSet);

        Object predictedClassValue =naiveBayesClassifier.classify(instanceForPrediction);
        return  predictedClassValue;
    }

    public Analysis getForecast (Instrument instrument){
        Analysis forecast = new Analysis();
         forecast.setForecastedBar(classification(4, instrument.getSymbol()).toString() );
         forecast.setSymbol(instrument.getSymbol());
        return forecast;
    }
}
