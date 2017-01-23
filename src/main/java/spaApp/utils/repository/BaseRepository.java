package spaApp.utils.repository;

import com.nurkiewicz.jdbcrepository.JdbcRepository;
import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.springframework.jdbc.core.RowMapper;
import spaApp.utils.repository.model.ModelDb;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRepository<E extends ModelDb> extends JdbcRepository<E, Long> {

    public BaseRepository(
            RowMapper<E> rowMapper,
            RowUnmapper<E> rowUnmapper,
            String tableName,
            String idColumn) {
        super(rowMapper, rowUnmapper, tableName, idColumn);
    }

    @Override
    protected <S extends E> S postCreate(S entity, Number generatedId) {
        if (generatedId != null) {
            entity.setId(generatedId.longValue());
        }
        return entity;
    }

    protected static Map<String, Object> mapOf(Object... values) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < values.length - 1; i += 2) {
            map.put(values[i].toString(), values[i + 1]);
        }
        return Collections.unmodifiableMap(map);
    }


}
