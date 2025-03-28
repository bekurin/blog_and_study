package troubleshooting.jpa.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import troubleshooting.jpa.controller.request.ProductCreateRequest;
import troubleshooting.jpa.controller.response.ProductResponse;
import troubleshooting.jpa.entity.Product;
import troubleshooting.jpa.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse saveProduct(ProductCreateRequest request) {
        Product product = request.toProduct();
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }
}
