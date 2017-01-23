package spaApp.naive_bayes_classifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spaApp.base_classifier.model.Analysis;
import spaApp.naive_bayes_classifier.service.NaiveBayesPerformanceService;
import spaApp.naive_bayes_classifier.service.NaiveBayesService;
import spaApp.rates.model.Instrument.Instrument;

@RestController
public class NaiveBayesController {

    @Autowired
    private NaiveBayesService naiveBayesService;

    @Autowired
    NaiveBayesPerformanceService naiveBayesPerformanceService;

    @RequestMapping(method = RequestMethod.GET, path = "/api/dataset")
    public Object analysis() {
        return naiveBayesService.classification(4, "GBP=X");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/forecast")
    public Analysis forecast(@RequestBody Instrument instrument) {
        return naiveBayesService.getForecast(instrument);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/efficiency")
    public Analysis analysis(@RequestBody Instrument instrument) {
        return naiveBayesPerformanceService.getAnalysis(instrument);
    }
}
