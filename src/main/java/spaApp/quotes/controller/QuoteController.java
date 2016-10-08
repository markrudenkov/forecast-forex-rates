package spaApp.quotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spaApp.quotes.model.Query.QueryWrapper;
import spaApp.quotes.service.QuoteService;

/*
 Generator of YQL has not been created yet!!!!!!!!!
 */

@RestController
public class QuoteController {
    //https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22EUR%3DX%22%20and%20startDate%20%3D%20%222009-09-11%22%20and%20endDate%20%3D%20%222010-03-10%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=
    private static String quote2 = "https://query.yahooapis.com/v1/public/yql?q=select * from yahoo.finance.historicaldata where symbol =\"EUR=X\" and startDate= \"2016-08-01\" and endDate= \"2016-08-04\"&format=json&env=store://datatables.org/alltableswithkeys";

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    QuoteService quoteService = new QuoteService();

    @RequestMapping("/query")
    public
    @ResponseBody
    String retrieve() {
        QueryWrapper response = restTemplate.getForObject(quote2, QueryWrapper.class);
            quoteService.appendQuerryWrapperToDB(response);

        return response.toString();
    }

  /*  @RequestMapping(method = RequestMethod.POST, path = "api/query")*/


}






