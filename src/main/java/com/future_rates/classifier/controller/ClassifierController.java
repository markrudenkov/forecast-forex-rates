package com.future_rates.classifier.controller;

import com.future_rates.classifier.service.ForecastService;
import com.future_rates.classifier.service.PerformanceService;
import com.future_rates.rates.model.Instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClassifierController {

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private PerformanceService performanceService;

    @RequestMapping(method = RequestMethod.POST, path = "/api/analysis/forecast")
    public Instrument forecast(@RequestBody Instrument instrument) {
        return forecastService.getForecast(instrument);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/analysis/performance")
    public Instrument analysis(@RequestBody Instrument instrument) {
        return performanceService.getAnalysis(instrument);
    }
}
