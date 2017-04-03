package com.future_rates.financial_instruments.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.future_rates.financial_instruments.service.FinInstrumentService;

@RestController
public class FinInstrumentsController {


    @Autowired
    FinInstrumentService service;

    @RequestMapping(method = RequestMethod.GET, path = "/api/instruments")
    public ArrayNode selectInstrument() {
        return service.getInstrumentList();
    }

}
