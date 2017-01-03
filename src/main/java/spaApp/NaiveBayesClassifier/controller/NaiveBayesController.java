package spaApp.NaiveBayesClassifier.controller;

import net.sf.javaml.core.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spaApp.NaiveBayesClassifier.model.Forecast;
import spaApp.NaiveBayesClassifier.service.NaiveBayesService;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.Rate;

import java.util.List;

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
