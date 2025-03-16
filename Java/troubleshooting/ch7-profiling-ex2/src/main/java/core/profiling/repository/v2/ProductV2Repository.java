package core.profiling.repository.v2;

import core.profiling.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductV2Repository extends JpaRepository<Product, Integer> {
}
