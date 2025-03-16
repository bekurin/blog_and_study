package core.profiling.repository.v1;

import core.profiling.model.ProductModel;
import core.profiling.repository.v1.mapper.ProductV1RowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductV1Repository {
    private final JdbcTemplate jdbcTemplate;

    public ProductModel findProduct(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ProductV1RowMapper(), id);
    }
}
