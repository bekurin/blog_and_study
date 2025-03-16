package core.profiling.repository.v2;

import core.profiling.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseV2Repository extends JpaRepository<Purchase, Integer> {
}
