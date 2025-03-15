package remote.debug.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import remote.debug.model.entity.Product;
import remote.debug.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InsertData {
    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        ArrayList<Product> products = new ArrayList<>(Arrays.asList(
                new Product("beer", BigDecimal.valueOf(5000), 100),
                new Product("coke", BigDecimal.valueOf(2000), 40),
                new Product("lemonade", BigDecimal.valueOf(10000), 1),
                new Product("chocolate", BigDecimal.valueOf(6000), null),
                new Product("candy", BigDecimal.valueOf(4000), 250)
        ));
        productRepository.saveAll(products);
    }
}
