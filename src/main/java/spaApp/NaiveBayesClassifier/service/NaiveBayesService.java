package spaApp.NaiveBayesClassifier.service;


import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.filter.discretize.RecursiveMinimalEntropyPartitioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spaApp.NaiveBayesClassifier.model.DataSet;
import spaApp.rates.model.Rate.LocalRate;
import spaApp.rates.service.RateService;

import java.util.List;

@Service
public class NaiveBayesService {

    @Autowired
    RateService rateService;

    public Dataset createDataSet(int atributes, String currency) {
        List<LocalRate> rates = rateService.getAllCurrencyRates(currency);
        DataSet dataset = new DataSet();
        dataset.setAtributes(atributes);
        return dataset.buildDataSet(dataset.getAtributes(), rates);

    }

    public Dataset dataSetDicretisation(int atributes, String currency){
        boolean sparse = false;
        Dataset nonDicretizedDataSet = createDataSet( atributes,  currency);
        Dataset dicretizedDataSet = nonDicretizedDataSet.copy();
        RecursiveMinimalEntropyPartitioning recursiveMinimalEntropyPartitioning = new RecursiveMinimalEntropyPartitioning(sparse);
        recursiveMinimalEntropyPartitioning.build(nonDicretizedDataSet);
        recursiveMinimalEntropyPartitioning.filter(dicretizedDataSet);


        return dicretizedDataSet;
    }

}
