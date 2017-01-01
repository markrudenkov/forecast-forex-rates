package spaApp.NaiveBayesClassifier.controller;

import net.sf.javaml.core.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spaApp.NaiveBayesClassifier.service.NaiveBayesService;

@RestController
public class NaiveBayesController {

    @Autowired
    private NaiveBayesService naiveBayesService;


    @RequestMapping(method = RequestMethod.GET, path = "/api/dataset")
    public Object analysis() {
        return naiveBayesService.classification(4,"GBP=X");
    }
}
