package spaApp.results.repository;

import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spaApp.results.repository.model.TradingResultsDb;
import spaApp.utils.repository.BaseRepository;

@Repository
public class TradingResultsRepository extends BaseRepository<TradingResultsDb> {

    @Autowired
    private JdbcTemplate template;

    private static final RowMapper<TradingResultsDb> ROW_MAPPER = (rs, rowNum) -> {
        TradingResultsDb db = new TradingResultsDb();
        db.setId(rs.getLong("id"));
        db.setTradingStartDate(new DateTime(rs.getDate("trading_start_date")));
        db.setTradingEndDate(new DateTime(rs.getDate("trading_end_date")));
        db.setProfitPoints(rs.getBigDecimal("profit_points"));
        db.setProfitUSD(rs.getBigDecimal("profit_usd"));
        db.setClassifier(rs.getString("classifier_type"));
        db.setConceptInstanceSize(rs.getInt("concept_instance_size"));
        db.setStopLossOrder(rs.getBigDecimal("stop_loss"));
        db.setTakeProfitOrder(rs.getBigDecimal("take_profit"));
        db.setTrailingStopLossOrder(rs.getBigDecimal("trailing_stop_loss"));
        db.setTrailingTakeProfitOrder(rs.getBigDecimal("trailing_take_profit"));
        return db;
    };


    private static final RowUnmapper<TradingResultsDb> ROW_UNMAPPER = db -> mapOf(
            "id", db.getId(),
            "trading_start_date", db.getTradingStartDate(),
            "trading_end_date", db.getTradingEndDate(),
            "profit_points", db.getProfitPoints(),
            "profit_usd", db.getProfitUSD(),
            "classifier_type", db.getClassifier(),
            "concept_instance_size", db.getConceptInstanceSize(),
            "stop_loss", db.getStopLossOrder(),
            "take_profit",db.getTakeProfitOrder(),
            "trailing_stop_loss", db.getTrailingStopLossOrder(),
            "trailing_take_profit", db.getTrailingTakeProfitOrder()
            );


    public TradingResultsRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "trading_results", "id");
    }
}
