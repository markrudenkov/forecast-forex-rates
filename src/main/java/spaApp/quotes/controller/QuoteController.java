package spaApp.quotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spaApp.quotes.model.Query.QueryWrapper;

/**
 Generator of YQL has not been created yet!!!!!!!!!
 */

@RestController
public class QuoteController {

    private static String quote2 = "https://query.yahooapis.com/v1/public/yql?q=select * from yahoo.finance.historicaldata where symbol =\"EUR=x\" and startDate= \"2016-08-01\" and endDate= \"2016-08-04\"&format=json&env=store://datatables.org/alltableswithkeys";

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/greeting")
    public
    @ResponseBody
    String retrieve() {
        QueryWrapper response = restTemplate.getForObject(quote2, QueryWrapper.class);
        return response.toString();
    }
}






