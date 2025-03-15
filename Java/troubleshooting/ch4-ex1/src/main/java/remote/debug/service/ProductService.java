package remote.debug.service;

import lombok.RequiredArgsConstructor;
import remote.debug.model.dto.TotalCostResponse;
import remote.debug.model.entity.Product;
import org.springframework.stereotype.Service;
import remote.debug.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public TotalCostResponse getTotalCosts() {
        TotalCostResponse response = new TotalCostResponse();
        try {
            var products = productRepository.findAll();

            var costs = products.stream()
                    .collect(Collectors.toMap(
                            Product::getName,
                            product -> product.getPrice().multiply(new BigDecimal(product.getQuantity()))));

            response.setTotalCosts(costs);
        } catch (Exception e) {
            e.printStackTrace(); // do nothing
        }

        return response;
    }
}

