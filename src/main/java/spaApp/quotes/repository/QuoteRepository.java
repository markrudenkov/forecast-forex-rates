package spaApp.quotes.repository;


import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spaApp.quotes.repository.model.QuoteDb;
import spaApp.utils.repository.BaseRepository;

import java.sql.Timestamp;

@Repository
public class QuoteRepository extends BaseRepository<QuoteDb> {

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


    public QuoteRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "quotes", "id");
    }
}
