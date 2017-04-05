package com.future_rates.rates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.service.RateService;

import java.util.List;

@RestController
public class RateController {

    @Autowired
    RateService rateService = new RateService();

    @RequestMapping(method = RequestMethod.POST, path = "/api/query")
    public List<Rate> selectInstrument(@RequestBody Instrument instrument) {
        return rateService.selectInstrument(instrument);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/allrates")
    public List<Rate> selectAllInstrument() {
        return rateService.selectAllInstrument();
    }

}






