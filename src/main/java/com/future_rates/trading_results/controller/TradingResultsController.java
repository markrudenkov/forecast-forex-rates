package com.future_rates.trading_results.controller;

import com.future_rates.trading_results.model.TradingResults;
import com.future_rates.trading_results.service.TradingResultsService;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradingResultsController {

    @Autowired
    TradingResultsService tradingResultsService;

//    @RequestMapping(method = RequestMethod.GET, path = "/api/trade")
//    public TradingResults getTradeResults() {
//        return tradingResultsService.trade("EUR/USD",new NaiveBayesClassifier(true, true, false),4,
//                true);
//    }

    @RequestMapping(method = RequestMethod.POST, path = "/api/trade")
    public TradingResults getTradeResultsWithOrders(@RequestBody TradingResults tradingResults) {
        return tradingResultsService.tradeWithOrders(tradingResults);
    }

}
