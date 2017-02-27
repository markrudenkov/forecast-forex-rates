package spaApp.rates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.QueryWrapper;
import spaApp.rates.model.Query.Rate;

import spaApp.rates.repository.RateRepository;
import spaApp.rates.repository.model.RateDb;

import java.util.ArrayList;
import java.util.List;


@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    public List<Rate> selectInstrument(Instrument api) {
        List<Rate> selectedInstrument = new ArrayList();
        for (RateDb quoteDb : repository.selectQuoteDb(api)) {
            selectedInstrument.add(mapToRate(quoteDb));
        }
        return selectedInstrument;
    }

    @Transactional
    public List<RateDb> appendQuerryWrapperToDB(QueryWrapper api) {
        List<RateDb> quoteDbList = QueryWrapperMapToQuoteDbList(api);
        createQuoteRecordInDB(quoteDbList);
        return null;
    }

    @Transactional
    public List<RateDb> createQuoteRecordInDB(List<RateDb> db) {
        for (RateDb quoteDb : db) {
            repository.create(quoteDb);
        }
        return db;
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
    public List<Rate> getLastCurrencyRates(int atributes,String currency){
        List<Rate> rates = new ArrayList<>();
        for (RateDb rate : repository.getLastCurrencyRates(atributes,currency)) {
            rates.add(mapToRate(rate));
        }
        return rates;
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
