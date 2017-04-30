package com.future_rates.classifiers_wrapper.model;


import libsvm.LibSVM;
import libsvm.SelfOptimizingLinearLibSVM;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.functions.SMO;

import java.util.Random;

public class ClassifiersWrapper{

    private SMO smo = new SMO();
    private Classifier bayes = new NaiveBayesClassifier(true, true, false);
    private Classifier svm = new LibSVM();
    private Classifier rndForest =  new RandomForest(500, false, 3, new Random(10));
    private Classifier svmSelfOpt = new SelfOptimizingLinearLibSVM();


    public Classifier getScmSelfOpt() {
        return svmSelfOpt;
    }

    public void setScmSelfOpt(Classifier scmSelfOpt) {
        this.svmSelfOpt = scmSelfOpt;
    }

    public Classifier getBayes() {
        return bayes;
    }

    public void setBayes(Classifier bayes) {
        this.bayes = bayes;
    }

    public Classifier getSvm() {
        return svm;
    }

    public void setSvm(Classifier svm) {
        this.svm = svm;
    }

    public Classifier getRndForest() {
        return rndForest;
    }

    public void setRndForest(Classifier rndForest) {
        this.rndForest = rndForest;
    }
}