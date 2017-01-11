package spaApp.classifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spaApp.classifier.model.Forecast;
import spaApp.classifier.service.NaiveBayesEfficiencyService;
import spaApp.rates.model.Instrument.Instrument;

@RestController
public class EfficiencyController {


    @Autowired
    NaiveBayesEfficiencyService naiveBayesEfficiencyService;

    @RequestMapping(method = RequestMethod.POST, path = "/api/efficiency")
    public Forecast analysis (@RequestBody Instrument instrument)  {
        return naiveBayesEfficiencyService.getAnalysis(instrument);
    }
}
