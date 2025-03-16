package core.profiling.repository.v1.mapper;

import core.profiling.model.PurchaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseV1RowMapper implements RowMapper<PurchaseModel> {

    @Override
    public PurchaseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PurchaseModel p = new PurchaseModel();

        p.setId(rs.getInt("id"));
        p.setProduct(rs.getInt("product"));
        p.setPrice(rs.getBigDecimal("price"));

        return p;
    }
}
