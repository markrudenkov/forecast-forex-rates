package com.future_rates.trading_results.controller;

import com.future_rates.trading_results.model.TradingSimulation;
import com.future_rates.trading_results.service.TradingResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradingResultsController {

    @Autowired
    TradingResultsService tradingResultsService;

    @RequestMapping(method = RequestMethod.POST, path = "/api/tarding_simulation")
    public TradingSimulation getTradeResultsWithOrders(@RequestBody TradingSimulation tradingSimulation) {
        return tradingResultsService.tradeWithOrders(tradingSimulation);
    }

}
