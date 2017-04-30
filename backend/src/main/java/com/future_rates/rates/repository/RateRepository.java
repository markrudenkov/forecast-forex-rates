package com.future_rates.rates.repository;


import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.future_rates.rates.model.Instrument.Instrument;
import com.future_rates.rates.repository.model.RateDb;
import com.future_rates.utils.repository.BaseRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class RateRepository extends BaseRepository<RateDb> {

    @Autowired
    private JdbcTemplate template;

    public static final String SELECT_RATES__BY_SYMBOL_AND_DATE = "SELECT * FROM rates WHERE symbol = ? AND date BETWEEN ? AND ?";
    public static final String SELECT_THE_LAST_CURRENCY_RATE = "SELECT * FROM rates WHERE symbol = ? ORDER BY date DESC LIMIT 1";
    public static final String SELECT_CURRENCY_RATES = "SELECT * FROM rates WHERE symbol = ? ORDER BY date ASC";
    public static final String SELECT_THE_LAST_CURRENCY_RATES = "SELECT * FROM rates WHERE symbol = ? ORDER BY date DESC LIMIT ?";
    public static final String SELECT_THE_FIRST_CURRENCY_RATE = "SELECT * FROM rates WHERE symbol = ? ORDER BY date ASC LIMIT 1";

    private static final RowMapper<RateDb> ROW_MAPPER = (rs, rowNum) -> {
        RateDb quoteDb = new RateDb();
        quoteDb.setDate(new DateTime(rs.getDate("date")));
        quoteDb.setSymbol(rs.getString("symbol"));
        quoteDb.setOpen(rs.getBigDecimal("open"));
        quoteDb.setHigh(rs.getBigDecimal("high"));
        quoteDb.setLow(rs.getBigDecimal("low"));
        quoteDb.setClose(rs.getBigDecimal("close"));
        quoteDb.setAdjustedClose(rs.getBigDecimal("adj_close"));
        return quoteDb;
    };

    private static final RowUnmapper<RateDb> ROW_UNMAPPER = quoteDb -> mapOf(
            "date", new Timestamp(quoteDb.getDate().getMillis()),
            "symbol", quoteDb.getSymbol(),
            "open", quoteDb.getOpen(),
            "high", quoteDb.getHigh(),
            "low", quoteDb.getLow(),
            "close", quoteDb.getClose(),
            "adj_close", quoteDb.getAdjustedClose()
    );

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public RateRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "rates", "id");
    }

    public List<RateDb> getRatesBySymbolAndDate(Instrument instrument) {
        List<RateDb> quoteDb = template.query(
                SELECT_RATES__BY_SYMBOL_AND_DATE,
                new Object[]{instrument.getSymbol(), new Timestamp(instrument.getStartDate().getMillis()), new Timestamp(instrument.getEndDate().getMillis())},
                ROW_MAPPER);
        return quoteDb;
    }


    public List<RateDb> getRatesBySymbolAndDate(String symbol, DateTime startDate, DateTime endDate) {
        List<RateDb> quoteDb = template.query(
                SELECT_RATES__BY_SYMBOL_AND_DATE,
                new Object[]{symbol, new Timestamp(startDate.getMillis()), new Timestamp(endDate.getMillis())},
                ROW_MAPPER);
        return quoteDb;
    }

    public RateDb selectLastEntry(String currency) {
        List<RateDb> rateDb = template.query(SELECT_THE_LAST_CURRENCY_RATE, new Object[]{currency}, ROW_MAPPER);
        return rateDb.get(0);
    }

    public List<RateDb> getAllCurrencyRates(String currency) {
        List<RateDb> rateDb = template.query(SELECT_CURRENCY_RATES, new Object[]{currency}, ROW_MAPPER);
        return rateDb;
    }

    public List<RateDb> getLastCurrencyRates(int atributes, String currency) {
        List<RateDb> rateDb = template.query(SELECT_THE_LAST_CURRENCY_RATES, new Object[]{currency, atributes}, ROW_MAPPER);
        return rateDb;
    }

    public RateDb selectFirstEntry(String symbol) {
        List<RateDb> rateDb = template.query(SELECT_THE_FIRST_CURRENCY_RATE, new Object[]{symbol}, ROW_MAPPER);
        return rateDb.get(0);
    }
}


