package spaApp.financial_instruments.repository;

import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spaApp.financial_instruments.repository.model.FinInstrumentDb;
import spaApp.utils.repository.BaseRepository;

@Repository
public class FinInstrumentRepository extends BaseRepository<FinInstrumentDb> {

    @Autowired
    private JdbcTemplate template;

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
}
