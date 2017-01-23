package spaApp.base_classifier.model;

import net.sf.javaml.core.Dataset;
import spaApp.rates.model.Query.Rate;

import java.util.ArrayList;
import java.util.List;

public class TrainingDataSet extends DataSet {

    public TrainingDataSet(int atributes) {
        super(atributes);
    }

    public Dataset buildTrainingDataSet(List<Rate> rates) {
        List<Rate> trainingRates = new ArrayList<>();
        for (int i = 0; i < rates.size() * getTrainingDataSetPercentage(); i++) {
            trainingRates.add(rates.get(i));
        }
        Dataset trainingDataset = buildDataSet(trainingRates);
        return trainingDataset;
    }
}
