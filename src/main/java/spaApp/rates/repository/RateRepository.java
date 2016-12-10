package spaApp.rates.repository;


import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spaApp.rates.model.Instrument.Instrument;
import spaApp.rates.repository.model.QuoteDb;
import spaApp.utils.repository.BaseRepository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class RateRepository extends BaseRepository<QuoteDb> {

    @Autowired
    private JdbcTemplate template;
    public static final String SELECT_RATES__BY_SYMBOL_AND_DATE = "SELECT * FROM rates WHERE symbol = ? AND date BETWEEN ? AND ?";

    private static final RowMapper<QuoteDb> ROW_MAPPER=(rs,rowNum)->{
        QuoteDb quoteDb = new QuoteDb();
        quoteDb.setId(rs.getLong("id"));
        quoteDb.setDate(new DateTime(rs.getDate("date")));
        quoteDb.setSymbol(rs.getString("symbol"));
        quoteDb.setOpen(rs.getBigDecimal("open"));
        quoteDb.setHigh(rs.getBigDecimal("high"));
        quoteDb.setLow(rs.getBigDecimal("low"));
        quoteDb.setClose(rs.getBigDecimal("close"));
        quoteDb.setAdjustedClose(rs.getBigDecimal("adj_close"));
        return quoteDb;
    };

    private static final RowUnmapper<QuoteDb> ROW_UNMAPPER = quoteDb -> mapOf(
      "id",quoteDb.getId(),
            "date", new Timestamp(quoteDb.getDate().getMillis()),
            "symbol",quoteDb.getSymbol(),
            "open", quoteDb.getOpen(),
            "high", quoteDb.getHigh(),
            "low",quoteDb.getLow(),
            "close",quoteDb.getClose(),
            "adj_close",quoteDb.getAdjustedClose()

    );

    public RateRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "rates", "id");
    }

    public List<QuoteDb> selectQuoteDb (Instrument instrument){
        List<QuoteDb> quoteDb =template.query(
                SELECT_RATES__BY_SYMBOL_AND_DATE,
                new Object[] {instrument.getSymbol(),new Timestamp(instrument.getStartDate().getMillis()), new Timestamp(instrument.getEndDate().getMillis())},
                ROW_MAPPER);
        return quoteDb;
}

}
