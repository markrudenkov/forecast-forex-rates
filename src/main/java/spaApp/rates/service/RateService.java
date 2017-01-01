package spaApp.rates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.QueryWrapper;
import spaApp.rates.model.Query.Rate;
import spaApp.rates.model.Rate.LocalRate;
import spaApp.rates.repository.RateRepository;
import spaApp.rates.repository.model.RateDb;

import java.util.ArrayList;
import java.util.List;


@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    @Autowired
    private RateService rateService;



    public List<LocalRate> selectInstrument(Instrument api) {
        List<LocalRate> selectedInstrument = new ArrayList();
        for (RateDb quoteDb : repository.selectQuoteDb(api)) {
            selectedInstrument.add(mapToLocalQuote(quoteDb));
        }
        return selectedInstrument;
    }

    @Transactional
    public List<RateDb> appendQuerryWrapperToDB(QueryWrapper api) {
        List<RateDb> quoteDbList = rateService.QueryWrapperMapToQuoteDbList(api);
        rateService.createQuoteRecordInDB(quoteDbList);
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
            quoteDbList.add(mapToQuoteDB(rate));
        }
        return quoteDbList;
    }

    @Transactional
    public List<LocalRate> getAllCurrencyRates(String currency) {
        List<LocalRate> rates = new ArrayList<>();
        for (RateDb rate : repository.getAllCurrencyRates(currency)) {
            rates.add(mapToLocalQuote(rate));
        }
        return rates;
    }

    @Transactional
    public List<LocalRate> getLastCurrencyRates(int atributes,String currency){
        List<LocalRate> rates = new ArrayList<>();
        for (RateDb rate : repository.getLastCurrencyRates(atributes,currency)) {
            rates.add(mapToLocalQuote(rate));
        }
        return rates;
    }

    public static RateDb mapToQuoteDB(Long id, Rate api) {
        RateDb db = new RateDb();
        db.setSymbol(api.getSymbol());
        db.setDate(api.getDate());
        db.setOpen(api.getOpen());
        db.setHigh(api.getHigh());
        db.setLow(api.getLow());
        db.setClose(api.getClose());
        db.setAdjustedClose(api.getAdjustedClose());

        return db;
    }

    public static LocalRate mapToLocalQuote(RateDb db) {
        LocalRate api = new LocalRate();
        api.setId(db.getId());
        api.setSymbol(db.getSymbol());
        api.setDate(db.getDate());
        api.setOpen(db.getOpen());
        api.setHigh(db.getHigh());
        api.setLow(db.getLow());
        api.setClose(db.getClose());
        api.setAdjustedClose(db.getAdjustedClose());

        return api;
    }

    private static RateDb mapToQuoteDB(Rate api) {
        return mapToQuoteDB(null, api);
    }

}
