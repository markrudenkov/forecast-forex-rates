package spaApp.rates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.Rate;
import spaApp.rates.service.RateService;

import java.util.List;

@RestController
public class RateController {

    @Autowired
    RateService rateService = new RateService();

    @RequestMapping(method = RequestMethod.POST, path = "/api/query")
    public List<Rate> selectInstrument (@RequestBody Instrument instrument)  {
        return rateService.selectInstrument(instrument);
    }

}






