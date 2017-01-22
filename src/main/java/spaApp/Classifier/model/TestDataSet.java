package spaApp.Classifier.model;

import net.sf.javaml.core.Dataset;
import spaApp.rates.model.Query.Rate;

import java.util.ArrayList;
import java.util.List;


public class TestDataSet extends DataSet{

    public TestDataSet(int atributes) {
        super(atributes);
    }

    public Dataset buildTestDataSet(int atributes, List<Rate> rates){
        List<Rate> testRates = new ArrayList<>();
        for(int i = (int) (rates.size()*getTrainingDataSetPercentage()); i < rates.size(); i++){
            testRates.add(rates.get(i));
        }
        Dataset testDataset = buildDataSet(testRates);
        return testDataset;
    }
}
