package core.profiling.repository.v1.mapper;

import core.profiling.model.ProductModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductV1RowMapper implements RowMapper<ProductModel> {

    @Override
    public ProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductModel p = new ProductModel();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        return p;
    }
}
