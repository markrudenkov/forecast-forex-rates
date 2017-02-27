package spaApp.financial_instruments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spaApp.financial_instruments.model.FinInstrument;
import spaApp.financial_instruments.repository.FinInstrumentRepository;
import spaApp.financial_instruments.repository.model.FinInstrumentDb;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FinInstrumentService {

    @Autowired
    FinInstrumentRepository finInstrumentRepository;

    public List<FinInstrument> getAllSymbolCodes (){
        return finInstrumentRepository.findAll().stream().map(FinInstrumentService::mapToFinInstrument).collect(Collectors.toList());
    }




    private static FinInstrumentDb mapToFinInstrumentDb (Long id, FinInstrument api){
       FinInstrumentDb db = new FinInstrumentDb();
       db.setId(id);
       db.setSymbol(api.getSymbol());
       db.setYahooCode(api.getYahooCode());
       db.setSpread(api.getSpread());
        return db;
    }

    private static FinInstrument mapToFinInstrument (FinInstrumentDb db){
        FinInstrument api = new FinInstrument();
        api.setId(db.getId());
        api.setSymbol(db.getSymbol());
        api.setYahooCode(db.getYahooCode());
        api.setSpread(db.getSpread());
        return api;
    }

    private static  FinInstrumentDb mapToFinInstrumentDb(FinInstrument api){
        return mapToFinInstrumentDb(null,api);
    }
}
