package com.future_rates.base_classifier.controller;

import com.future_rates.base_classifier.model.Analysis;
import com.future_rates.base_classifier.service.ForecastService;
import com.future_rates.naive_bayes_classifier.service.NaiveBayesPerformanceService;
import com.future_rates.naive_bayes_classifier.service.NaiveBayesService;
import com.future_rates.rates.model.Instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ForecastController {

    @Autowired
    private ForecastService forecastService;





    @RequestMapping(method = RequestMethod.POST, path = "/api/forecast/classifier")
    public Analysis forecast(@RequestBody Instrument instrument) {
        return forecastService.getForecast(instrument);
    }


}
