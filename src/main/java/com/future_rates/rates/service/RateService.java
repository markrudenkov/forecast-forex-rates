package com.future_rates.rates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.future_rates.financial_instruments.repository.FinInstrumentRepository;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.model.Query.QueryWrapper;
import com.future_rates.rates.model.Query.Rate;
import com.future_rates.rates.repository.RateRepository;
import com.future_rates.rates.repository.model.RateDb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    @Autowired
    private FinInstrumentRepository finInstrumentRepository;


    public List<Rate> selectInstrument(Instrument api) {
        List<Rate> selectedInstrument = new ArrayList();
        for (RateDb quoteDb : repository.selectQuoteDb(api)) {
            selectedInstrument.add(mapToRate(quoteDb));
        }
        return selectedInstrument;
    }

    public List<Rate> selectAllInstrument() {
        return repository.findAll().stream().map(RateService::mapToRate).collect(Collectors.toList());
    }


    @Transactional
    public void appendYahooRatesToDB(QueryWrapper api) {
        List<RateDb> rateDbList = QueryWrapperMapToQuoteDbList(api);
        createRateRecordInDB(changeYahooCodeToSymbol(rateDbList));
    }

    private List<RateDb> changeYahooCodeToSymbol(List<RateDb> quoteDbList) {
        String symbol = finInstrumentRepository.getFinInstrumentByYahooCode(quoteDbList.get(0).getSymbol()).getSymbol();
        quoteDbList.stream().forEach(s -> s.setSymbol(symbol));
        return quoteDbList;
    }

    private static void mapToSymbol(RateDb db, String symbol) {
        db.setSymbol(symbol);
    }

    @Transactional
    public void createRateRecordInDB(List<RateDb> db) {
        db.stream().forEach(rateDb -> repository.create(rateDb));
    }

    @Transactional
    public List<RateDb> QueryWrapperMapToQuoteDbList(QueryWrapper api) {
        List<RateDb> quoteDbList = new ArrayList();
        for (Rate rate : api.getQuery().getResults().getQuote()) {
            quoteDbList.add(mapToRateDB(rate));
        }
        return quoteDbList;
    }

    @Transactional
    public List<Rate> getAllCurrencyRates(String currency) {
        List<Rate> rates = new ArrayList<>();
        for (RateDb rate : repository.getAllCurrencyRates(currency)) {
            rates.add(mapToRate(rate));
        }
        return rates;
    }

    @Transactional
    public Rate getLastEntry(String symbol) {
        RateDb lastEntryDB = repository.selectLastEntry(symbol);
        Rate lastEntry = mapToRate(lastEntryDB);
        return lastEntry;
    }

    @Transactional
    public List<Rate> getLastCurrencyRates(int atributes, String currency) {
        return repository.getLastCurrencyRates(atributes, currency).stream().map(RateService::mapToRate).collect(Collectors.toList());
    }

    public static RateDb mapToRateDB(Long id, Rate api) {
        RateDb db = new RateDb();
        db.setId(id);
        db.setSymbol(api.getSymbol());
        db.setDate(api.getDate());
        db.setOpen(api.getOpen());
        db.setHigh(api.getHigh());
        db.setLow(api.getLow());
        db.setClose(api.getClose());
        db.setAdjustedClose(api.getAdjustedClose());
        return db;
    }

    public static Rate mapToRate(RateDb db) {
        Rate api = new Rate();
        api.setSymbol(db.getSymbol());
        api.setDate(db.getDate());
        api.setOpen(db.getOpen());
        api.setHigh(db.getHigh());
        api.setLow(db.getLow());
        api.setClose(db.getClose());
        api.setAdjustedClose(db.getAdjustedClose());
        return api;
    }

    private static RateDb mapToRateDB(Rate api) {
        return mapToRateDB(null, api);
    }

}
