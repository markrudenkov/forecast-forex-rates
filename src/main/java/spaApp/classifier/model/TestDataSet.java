package spaApp.classifier.model;

import net.sf.javaml.core.Dataset;
import spaApp.rates.model.Query.Rate;

import java.util.ArrayList;
import java.util.List;


public class TestDataSet extends DataSet{

    public Dataset buildTestDataSet(int atributes, List<Rate> rates){
        List<Rate> testRates = new ArrayList<>();
        for(int i = (int) (rates.size()*getTrainingDataSetPercentage()); i < rates.size(); i++){
            testRates.add(rates.get(i));
        }
        Dataset testDataset = buildDataSet(atributes,testRates);
        return testDataset;
    }
}
