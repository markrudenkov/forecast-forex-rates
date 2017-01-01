package spaApp.NaiveBayesClassifier.model;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.apache.commons.lang3.ArrayUtils;
import spaApp.rates.model.Query.Rate;
import java.util.ArrayList;
import java.util.List;

public class RateInstance {

    protected int atributes;

    protected Instance createInstance(int classValuePosition, List<Rate> rates) {
        Instance instance = new DenseInstance(getAtributesArray(classValuePosition, rates));
        instance.setClassValue(estimateClassValue(classValuePosition, rates));
        return instance;
    }

    public  Instance createUnclasifiedInstance(int atributes, List<Rate> rates){
        Instance instance = new DenseInstance(getAtributesArray(atributes, rates));
        return instance;
    }

    private double[] getAtributesArray(int classValuePosition, List<Rate> rates) {
        List<Double> atributesList = new ArrayList<Double>();
        for (int j = classValuePosition - atributes; j < classValuePosition; j++) {
            Rate rate = rates.get(j);
            atributesList.add(rate.getOpen().doubleValue());
            atributesList.add(rate.getHigh().doubleValue());
            atributesList.add(rate.getLow().doubleValue());
            atributesList.add(rate.getClose().doubleValue());
        }
        double[] instanceAtributes = ArrayUtils.toPrimitive(atributesList.toArray(new Double[atributesList.size()]));
        return instanceAtributes;
    }

    private String estimateClassValue(int classValuePosition, List<Rate> rates) {
        Rate rate = rates.get(classValuePosition);
        String classValue;
        if (rate.getOpen().doubleValue() - rate.getClose().doubleValue() > 0) {
            classValue = "black";
        } else {
            classValue = "white";
        }
        return classValue;
    }

    public int getAtributes() {
        return atributes;
    }

    public void setAtributes(int atributes) {
        this.atributes = atributes;
    }
}
