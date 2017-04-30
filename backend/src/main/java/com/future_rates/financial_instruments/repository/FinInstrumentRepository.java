package com.future_rates.financial_instruments.repository;

import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.future_rates.financial_instruments.repository.model.FinInstrumentDb;
import com.future_rates.utils.repository.BaseRepository;

import java.util.List;

@Repository
public class FinInstrumentRepository extends BaseRepository<FinInstrumentDb> {

    @Autowired
    private JdbcTemplate template;
    public static final String SELECT_BY_YAHOO_CODE = "SELECT * FROM financial_instruments WHERE yahoo_code = ?";
    public static final String SELECT_BY_SYMBOL = "SELECT * FROM financial_instruments WHERE symbol = ?";

    private static final RowMapper<FinInstrumentDb> ROW_MAPPER = (rs, rowNum) -> {
        FinInstrumentDb db = new FinInstrumentDb();
        db.setId(rs.getLong("id"));
        db.setSymbol(rs.getString("symbol"));
        db.setYahooCode(rs.getString("yahoo_code"));
        db.setSpread(rs.getBigDecimal("spread"));
        return db;
    };

    private static final RowUnmapper<FinInstrumentDb> ROW_UNMAPPER = finInstrumentDb -> mapOf(
            "id", finInstrumentDb.getId(),
            "symbol", finInstrumentDb.getSymbol(),
            "yahoo_code", finInstrumentDb.getYahooCode(),
            "spread", finInstrumentDb.getSpread()
    );

    public FinInstrumentRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "financial_instruments", "id");
    }

    public FinInstrumentDb getFinInstrumentByYahooCode(String yahooCode) {
        List<FinInstrumentDb> finInstrumentDbList = template.query(SELECT_BY_YAHOO_CODE, new Object[]{yahooCode},  ROW_MAPPER);
        return finInstrumentDbList.get(0);
    }

    public FinInstrumentDb getFinInstrumentBySymbol(String symbol) {
        List<FinInstrumentDb> finInstrumentDbList = template.query(SELECT_BY_SYMBOL, new Object[]{symbol},  ROW_MAPPER);
        return finInstrumentDbList.get(0);
    }
}
