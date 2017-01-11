package spaApp.classifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spaApp.classifier.model.Forecast;
import spaApp.classifier.service.NaiveBayesService;
import spaApp.rates.model.Instrument.Instrument;

@RestController
public class NaiveBayesController {

    @Autowired
    private NaiveBayesService naiveBayesService;


    @RequestMapping(method = RequestMethod.GET, path = "/api/dataset")
    public Object analysis() {
        return naiveBayesService.classification(4,"GBP=X");
    }


    @RequestMapping(method = RequestMethod.POST, path = "/api/analysis")
    public Forecast analysis (@RequestBody Instrument instrument)  {
        return naiveBayesService.getAnalysis(instrument);
    }
}
