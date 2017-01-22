package spaApp.Classifier.model;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;

import org.springframework.beans.factory.annotation.Autowired;
import spaApp.rates.model.Query.Rate;
import spaApp.rates.repository.RateRepository;
import spaApp.rates.service.RateService;

import java.util.List;


public class DataSet extends RateInstance{

    private Dataset dataset;
    private double trainingDataSetPercentage = 0.7;

    public DataSet(int atributes) {
        super(atributes);
    }

    public Dataset buildDataSet(List<Rate> rates) {
        Dataset ratesDS = new DefaultDataset();
        for (int i = this.atributes; i < rates.size(); i++) {
            ratesDS.add(createInstance(i, rates));
        }
        return ratesDS;
    }

    public double getTrainingDataSetPercentage() {
        return trainingDataSetPercentage;
    }
}
