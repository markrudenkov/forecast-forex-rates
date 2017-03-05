package spaApp.financial_instruments.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public List<FinInstrument> getAllSymbolCodes() {
        return finInstrumentRepository.findAll().stream().map(FinInstrumentService::mapToFinInstrument).collect(Collectors.toList());
    }

    private static FinInstrumentDb mapToFinInstrumentDb(Long id, FinInstrument api) {
        FinInstrumentDb db = new FinInstrumentDb();
        db.setId(id);
        db.setSymbol(api.getSymbol());
        db.setYahooCode(api.getYahooCode());
        db.setSpread(api.getSpread());
        return db;
    }

    private static FinInstrument mapToFinInstrument(FinInstrumentDb db) {
        FinInstrument api = new FinInstrument();
        api.setId(db.getId());
        api.setSymbol(db.getSymbol());
        api.setYahooCode(db.getYahooCode());
        api.setSpread(db.getSpread());
        return api;
    }

    private static FinInstrumentDb mapToFinInstrumentDb(FinInstrument api) {
        return mapToFinInstrumentDb(null, api);
    }

    public ArrayNode getInstrumentList() {
        List <FinInstrument> finInstrumentList = finInstrumentRepository.findAll().stream().map(FinInstrumentService::mapToFinInstrument).collect(Collectors.toList());
        List <ObjectNode> symbolList = finInstrumentList.stream().map(FinInstrumentService::mapToObjectNode).collect(Collectors.toList());
        ArrayNode symbolListJson = new ObjectMapper().createArrayNode();
        symbolList.stream().forEach(obj ->symbolListJson.add(obj));
        return symbolListJson;
    }

    private static ObjectNode mapToObjectNode (FinInstrument api){
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("symbol",api.getSymbol());
        return objectNode;
    }
}
