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
    private static String quote2 = "https://query.yahooapis.com/v1/public/yql?q=select * from yahoo.finance.historicaldata where symbol =\"EUR=X\" and startDate= \"2016-08-01\" and endDate= \"2016-08-04\"&format=json&env=store://datatables.org/alltableswithkeys";

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    RateService rateService = new RateService();

    @RequestMapping("/query")
    public
    @ResponseBody
    String retrieve() {
        QueryWrapper response = restTemplate.getForObject(quote2, QueryWrapper.class);
            rateService.appendQuerryWrapperToDB(response);

        return response.toString();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/query")
    public List<LocalRate> selectInstrument (@RequestBody Instrument instrument)  {
        return rateService.selectInstrument(instrument);
    }

}






