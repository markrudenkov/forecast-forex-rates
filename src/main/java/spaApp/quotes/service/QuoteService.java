package spaApp.quotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spaApp.quotes.model.Query.QueryWrapper;
import spaApp.quotes.model.Query.Quote;
import spaApp.quotes.model.Quote.LocalQuote;
import spaApp.quotes.repository.QuoteRepository;
import spaApp.quotes.repository.model.QuoteDb;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuoteService {

    @Autowired
    private QuoteRepository repository;

    @Autowired
    private QuoteService quoteService;

    @Transactional
    public List<QuoteDb> appendQuerryWrapperToDB(QueryWrapper api)  {
        List<QuoteDb> quoteDbList =  quoteService.QueryWrapperMapToQuoteDbList(api);
        quoteService.createQuoteRecordInDB(quoteDbList);
       return quoteDbList;
    }

    @Transactional
    public List<QuoteDb> createQuoteRecordInDB (List<QuoteDb> db) {
        /*List<QuoteDb> quoteDbList = db;*/
        for(QuoteDb quoteDb : db){
            repository.create(quoteDb);
        }
        return db;
    }

    @Transactional
    public List<QuoteDb> QueryWrapperMapToQuoteDbList (QueryWrapper api) {
        List<QuoteDb> quoteDbList = new ArrayList();
        for(Quote quote: api.getQuery().getResults().getQuote()){
            quoteDbList.add(mapToQuoteDB(quote));
        }
        return  quoteDbList;

    }

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

    private static QuoteDb mapToQuoteDB (Quote api){
        return mapToQuoteDB(null,api);
    }

}
