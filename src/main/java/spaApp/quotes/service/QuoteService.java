package spaApp.quotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spaApp.quotes.model.Query.Quote;
import spaApp.quotes.model.Quote.LocalQuote;
import spaApp.quotes.repository.QuoteRepository;
import spaApp.quotes.repository.model.QuoteDb;

/**
 * Created by mark on 16.9.15.
 */
@Service
public class QuoteService {

    @Autowired
    private QuoteRepository repository;


    public static QuoteDb mapToQuoteDB (Long id, Quote api){
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


    public static LocalQuote mapToLocalQuote (QuoteDb db){
        LocalQuote api =new LocalQuote();
        api.setId(db.getId());
        api.setSymbol(db.getSymbol());
        api.setOpen(db.getOpen());
        api.setHigh(db.getHigh());
        api.setLow(db.getLow());
        api.setClose(db.getClose());
        api.setAdjustedClose(db.getAdjustedClose());

        return api;
    }

}
