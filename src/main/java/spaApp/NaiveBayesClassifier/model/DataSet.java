package spaApp.NaiveBayesClassifier.model;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import spaApp.rates.model.Rate.LocalRate;
import spaApp.rates.repository.RateRepository;
import spaApp.rates.service.RateService;

import java.util.ArrayList;
import java.util.List;


public class DataSet extends RateInstance{

    private Dataset dataset;

    @Autowired
    RateService rateService;

    @Autowired
    RateRepository rateRepository;

    //RateInstance rateInstance;

    public Dataset buildDataSet(int atributes, List<LocalRate> rates) {
        Dataset ratesDS = new DefaultDataset();
       // rateInstance.setAtributes(atributes);
        for (int i = atributes; i < rates.size(); i++) {
            ratesDS.add(createInstance(i, rates));
        }
        return ratesDS;
    }
}
