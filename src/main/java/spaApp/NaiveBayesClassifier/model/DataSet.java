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


public class DataSet {
    private int atributes;
    private Dataset dataset;


    @Autowired
    RateService rateService;

    @Autowired
    RateRepository rateRepository;

    public Dataset buildDataSet(int atributes, List<LocalRate> rates) {
        Dataset ratesDS = new DefaultDataset();
        for (int i = atributes; i < rates.size(); i++) {
            ratesDS.add(createInstance(i, rates));
        }
        return ratesDS;
    }

    private Instance createInstance(int classValuePosition, List<LocalRate> rates) {

        Instance instance = new DenseInstance(getAtributesArray(classValuePosition, rates));
        instance.setClassValue(estimateClassValue(classValuePosition, rates));
        return instance;
    }

    private double[] getAtributesArray(int classValuePosition, List<LocalRate> rates) {
        List<Double> atributesList = new ArrayList<Double>();
        for (int j = classValuePosition - atributes; j < classValuePosition; j++) {
            LocalRate rate = rates.get(j);
            atributesList.add(rate.getOpen().doubleValue());
            atributesList.add(rate.getHigh().doubleValue());
            atributesList.add(rate.getLow().doubleValue());
            atributesList.add(rate.getClose().doubleValue());
        }
        double[] instanceAtributes = ArrayUtils.toPrimitive(atributesList.toArray(new Double[atributesList.size()]));
        return instanceAtributes;
    }

    private String estimateClassValue(int classValuePosition, List<LocalRate> rates) {
        LocalRate rate = rates.get(classValuePosition);
        String classValue;
        if (rate.getOpen().doubleValue() - rate.getClose().doubleValue() > 0) {
            classValue = "black";
        } else {
            classValue = "black";
        }
        return classValue;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "dataset=" + dataset +
                '}';
    }

    public int getAtributes() {
        return atributes;
    }

    public void setAtributes(int atributes) {
        this.atributes = atributes;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
