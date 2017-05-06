package com.future_rates.classifier.service;

import com.future_rates.classifier.model.DataSet;
import com.future_rates.classifiers_wrapper.model.ClassifiersWrapper;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.service.RateService;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.discretize.RecursiveMinimalEntropyPartitioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PerformanceService extends ClassifierService {

    @Autowired
    RateService rateService;

    private final int  numberOfChecks = 100;
    private final double trainingSetPart = 0.7;
    private DefaultDataset learningSet = new DefaultDataset();
    private DefaultDataset trainingSet = new DefaultDataset();
    private DefaultDataset testSet = new DefaultDataset();;
    private List<Double> accuracySet;
    Classifier clf;

    private PerformanceService getDataForClassification(Instrument instrument) {
        this.learningSet = (DefaultDataset) createDataSet(instrument.getInstancePeriod(), instrument.getSymbol());
        this.accuracySet = new ArrayList();
        this.clf = (Classifier) selectClassifier(instrument.getClassifierName());
        return this;
    }

    //Returns Classifiers performance of randomly generated sets
    public double getAveragePerformance(Instrument instrument) {
        for (int i = 0; i < numberOfChecks; i++) {
            Collections.shuffle(this.learningSet);
            Dataset trainingSet = getTrainigSet(learningSet);
            Dataset testSet = getTestSet(learningSet);
            if (instrument.getClassifierName() == "Naive Bayes") {
                filterTestSetAndTrainingSet(testSet, trainingSet);
            }
            clf.buildClassifier(trainingSet);
            accuracySet.add(getClassifierPerformance(testSet, clf));
        }
        return countAveragePerformance(accuracySet);
    }

    public Dataset getTrainigSet (Dataset learningSet){
        Dataset trainingSet = new DefaultDataset();
          learningSet.subList(0, (int) (learningSet.size()*trainingSetPart)).parallelStream().forEach(s -> trainingSet.add(s));
        return trainingSet;
    }

    public Dataset getTestSet (Dataset learningSet){
        Dataset testSet = new DefaultDataset();
        learningSet.subList((int) (learningSet.size()*trainingSetPart), learningSet.size()).stream().forEach(s -> testSet.add(s));
        return testSet;
    }

    public DefaultDataset  getDefaultTestSet (Dataset learningSet){
        DefaultDataset testSet = new DefaultDataset();
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
        return  correct * 100 / (testSet.size());
    }

    protected double countAveragePerformance(List<Double> accuracySet) {
        return accuracySet.stream().reduce(0.0,Double::sum)/ accuracySet.size();
    }

    protected Dataset createDataSet(int atributes, String currency) {
        return  new DataSet(atributes).buildDataSet(rateService.getAllCurrencyRates(currency));
    }

    protected void filterDatasetAndInstance(DefaultDataset dataSet, DefaultDataset testSet) {
        RecursiveMinimalEntropyPartitioning rmep = new RecursiveMinimalEntropyPartitioning(true);    //Data dicretisation by Recursive Minimal Entropy Partitioning
        rmep.build(dataSet);
        rmep.filter(dataSet);
        rmep.filter(testSet);

    }



    Classifier naiveBayesClassifier = new NaiveBayesClassifier(true, true, false);

    public Instrument getAnalysis(Instrument instrument) {
        instrument.setAccuraccy(
                String.valueOf(
                        getDataForClassification(instrument).
                                getAveragePerformance(instrument)));
        return instrument;
    }
}
