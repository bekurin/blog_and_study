package core.profiling.repository.v1;

import core.profiling.model.PurchaseModel;
import core.profiling.repository.v1.mapper.PurchaseV1RowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseV1Repository {
    private final JdbcTemplate jdbcTemplate;

    public List<PurchaseModel> findAll() {
        String sql = "SELECT * FROM purchase";
        return jdbcTemplate.query(sql, new PurchaseV1RowMapper());
    }
}
