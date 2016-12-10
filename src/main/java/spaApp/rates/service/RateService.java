package spaApp.rates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.model.Query.QueryWrapper;


import spaApp.rates.model.Query.Rate;
import spaApp.rates.model.Rate.LocalRate;
import spaApp.rates.repository.RateRepository;
import spaApp.rates.repository.model.QuoteDb;

import java.util.ArrayList;
import java.util.List;


@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    @Autowired
    private RateService rateService;


    public List<LocalRate> selectInstrument(Instrument api){
        List<LocalRate> selectedInstrument = new ArrayList();
        for(QuoteDb quoteDb: repository.selectQuoteDb(api)){
            selectedInstrument.add(mapToLocalQuote(quoteDb));
        }
        return selectedInstrument;
    }

    @Transactional
    public List<QuoteDb> appendQuerryWrapperToDB(QueryWrapper api)  {
        List<QuoteDb> quoteDbList =  rateService.QueryWrapperMapToQuoteDbList(api);
        rateService.createQuoteRecordInDB(quoteDbList);
       return null;
    }

    @Transactional
    public List<QuoteDb> createQuoteRecordInDB (List<QuoteDb> db) {
        for(QuoteDb quoteDb : db){
            repository.create(quoteDb);
        }
        return db;
    }

    @Transactional
    public List<QuoteDb> QueryWrapperMapToQuoteDbList (QueryWrapper api) {
        List<QuoteDb> quoteDbList = new ArrayList();
        for(Rate rate: api.getQuery().getResults().getRate()){
            quoteDbList.add(mapToQuoteDB(rate));
        }
        return  quoteDbList;

    }

    public static QuoteDb mapToQuoteDB (Long id, Rate api){
        QuoteDb db = new QuoteDb();
        db.setSymbol(api.getSymbol());
        db.setDate(api.getDate());
        db.setOpen(api.getOpen());
        db.setHigh(api.getHigh());
        db.setLow(api.getLow());
        db.setClose(api.getClose());
        db.setAdjustedClose(api.getAdjustedClose());

        return db;
    }

    public static LocalRate mapToLocalQuote (QuoteDb db){
        LocalRate api =new LocalRate();
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

    private static QuoteDb mapToQuoteDB (Rate api){
        return mapToQuoteDB(null,api);
    }

}
