package com.future_rates.classifiers_wrapper.service;

import com.future_rates.classifiers_wrapper.model.ClassifiersWrapper;
import org.springframework.stereotype.Service;


@Service
public class ClassifierWrapperService {

    Object selectClassifier (String classifierName){
        ClassifiersWrapper clfWrapper = new ClassifiersWrapper();
        Object classifier = new Object();
        switch (classifierName){
            case "Naive Bayes" :
                classifier =  clfWrapper.getBayes();
            case "Support Vector Machines" :
                classifier = clfWrapper.getSvm();
            case "Random Forest" :
                classifier = clfWrapper.getRndForest();
            case "svmSelfOpt" :
                classifier = clfWrapper.getScmSelfOpt();
        }
        return classifier;
    }
}
