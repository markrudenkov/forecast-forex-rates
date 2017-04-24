package com.future_rates.base_classifier.service;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.discretize.RecursiveMinimalEntropyPartitioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.future_rates.base_classifier.model.DataSet;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.service.RateService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClassifierService {

    @Autowired
    RateService rateService;

    private final int  numberOfChecks = 100;
    private final double trainingSetPart = 0.7;

    //Returns Classifiers performance of randomly generated sets
    public double getAveragePerformance(int atributes, String currency, Classifier classifier, boolean datafilter) {
        List<Rate> rates = rateService.getAllCurrencyRates(currency);
        Dataset learningSet = new DataSet(atributes).buildDataSet(rates);
        List<Double> accuracySet = new ArrayList();
        for (int i = 0; i < numberOfChecks; i++) {
            Collections.shuffle(learningSet);
            Dataset trainingSet = getTrainigSet(learningSet);
            Dataset testSet = getTestSet(learningSet);
            if (datafilter) {
                filterTestSetAndTrainingSet(testSet, trainingSet);
            }
            classifier.buildClassifier(trainingSet);
            accuracySet.add(getClassifierPerformance(testSet, classifier));
        }
        return countAveragePerformance(accuracySet);
    }

    public Dataset getTrainigSet (Dataset learningSet){
        Dataset trainingSet = new DefaultDataset();
        learningSet.subList(0, (int) (learningSet.size()*trainingSetPart)).stream().forEach(s -> trainingSet.add(s));
        return trainingSet;
    }

    public Dataset getTestSet (Dataset learningSet){
        Dataset testSet = new DefaultDataset();
        learningSet.subList((int) (learningSet.size()*trainingSetPart), learningSet.size()).stream().forEach(s -> testSet.add(s));
        return testSet;
    }

    //Data dicretisation by Recursive Minimal Entropy Partitioning
    public void filterTestSetAndTrainingSet(Dataset testSet, Dataset trainingSet) {
        RecursiveMinimalEntropyPartitioning rmep = new RecursiveMinimalEntropyPartitioning(true);
        rmep.build(trainingSet);
        rmep.filter(trainingSet);
        rmep.filter(testSet);
    }

    //Returns Classifiers performance per dataset
    protected double getClassifierPerformance(Dataset testSet, Classifier classifier) {
        int correct = 0;
        for (Instance instance : testSet) {
            Object predictedClassValue = classifier.classify(instance);
            Object realClassValue = instance.classValue();
            if (predictedClassValue.equals(realClassValue)) {
                correct++;
            }
        }
        double performance = correct * 100 / (testSet.size());
        return performance;
    }

    protected double countAveragePerformance(List<Double> accuracySet) {
        double averageAccurracy = 0;
        for (double accuracy : accuracySet) {
            averageAccurracy += accuracy;
        }
        averageAccurracy /= accuracySet.size();
        return averageAccurracy;
    }

    protected Dataset createDataSet(int atributes, String currency) {
        List<Rate> rates = rateService.getAllCurrencyRates(currency);
        DataSet dataset = new DataSet(atributes);
        return dataset.buildDataSet(rates);
    }

    protected void filterDatasetAndInstance(Dataset dataSet, Instance instance) {
        RecursiveMinimalEntropyPartitioning rmep = new RecursiveMinimalEntropyPartitioning(true);    //Data dicretisation by Recursive Minimal Entropy Partitioning
        rmep.build(dataSet);
        rmep.filter(dataSet);
        rmep.filter(instance);

    }
}
