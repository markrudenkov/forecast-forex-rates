package spaApp.NaiveBayesClassifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spaApp.Classifier.model.Analysis;
import spaApp.NaiveBayesClassifier.service.NaiveBayesPerformanceService;
import spaApp.NaiveBayesClassifier.service.NaiveBayesService;
import spaApp.rates.model.Instrument.Instrument;

@RestController
public class NaiveBayesController {

    @Autowired
    private NaiveBayesService naiveBayesService;

    @Autowired
    NaiveBayesPerformanceService naiveBayesPerformanceService;

    @RequestMapping(method = RequestMethod.GET, path = "/api/dataset")
    public Object analysis() {
        return naiveBayesService.classification(4,"GBP=X");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/forecast")
    public Analysis forecast (@RequestBody Instrument instrument)  {
        return naiveBayesService.getForecast(instrument);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/efficiency")
    public Analysis analysis (@RequestBody Instrument instrument)  {
        return naiveBayesPerformanceService.getAnalysis(instrument);
    }
}
