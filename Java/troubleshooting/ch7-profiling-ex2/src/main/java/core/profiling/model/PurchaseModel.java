package core.profiling.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseModel {
    private int id;
    private int product;
    private BigDecimal price;
}
