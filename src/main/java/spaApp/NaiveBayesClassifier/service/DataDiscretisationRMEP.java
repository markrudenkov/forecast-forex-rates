package spaApp.NaiveBayesClassifier.service;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.filter.discretize.RecursiveMinimalEntropyPartitioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Data dicretisation by Recursive Minimal Entropy Partitioning
 */

@Service
public class DataDiscretisationRMEP {

    private boolean sparse = false;
    RecursiveMinimalEntropyPartitioning recursiveMinimalEntropyPartitioning = new RecursiveMinimalEntropyPartitioning(sparse);
    private Dataset nonDicretizedDataSet = new DefaultDataset();
    private Dataset dicretizedDataSet = new DefaultDataset();
    private int atributes;

    @Autowired
    NaiveBayesService naiveBayesService;

    public Dataset dataSetDicretisation(int atributes, String currency) {

        this.atributes = atributes;
        this.nonDicretizedDataSet = naiveBayesService.createDataSet(atributes, currency);
        this.dicretizedDataSet = nonDicretizedDataSet.copy();

        recursiveMinimalEntropyPartitioning.build(nonDicretizedDataSet);
        recursiveMinimalEntropyPartitioning.filter(dicretizedDataSet);
        return dicretizedDataSet;
    }

    public int getAtributes() {
        return atributes;
    }
}
