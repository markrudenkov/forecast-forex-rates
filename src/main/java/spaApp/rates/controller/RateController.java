package spaApp.rates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.QueryWrapper;
import spaApp.rates.model.Rate.LocalRate;
import spaApp.rates.service.RateService;

import java.util.List;

@RestController
public class RateController {

    @Autowired
    RateService rateService = new RateService();

    @RequestMapping(method = RequestMethod.POST, path = "/api/query")
    public List<LocalRate> selectInstrument (@RequestBody Instrument instrument)  {
        return rateService.selectInstrument(instrument);
    }

}






