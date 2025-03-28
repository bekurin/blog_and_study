package troubleshooting.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import troubleshooting.jpa.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
