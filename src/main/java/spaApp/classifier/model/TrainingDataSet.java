package spaApp.classifier.model;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import spaApp.rates.model.Query.Rate;

import java.util.ArrayList;
import java.util.List;


public class TrainingDataSet extends DataSet {

    public Dataset buildTrainingDataSet(int atributes, List<Rate> rates){
        List<Rate> trainingRates = new ArrayList<>();
        for(int i=0; i < rates.size()*getTrainingDataSetPercentage();i++){
            trainingRates.add(rates.get(i));
        }
        Dataset trainingDataset = buildDataSet(atributes,trainingRates);
        return trainingDataset;
    }
}
