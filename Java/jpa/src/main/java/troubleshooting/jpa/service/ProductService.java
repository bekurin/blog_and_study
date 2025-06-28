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

    @Transactional
    public Product updateProduct(final Long productId) {
        Product foundProduct = productRepository.findById(productId).get();
        foundProduct.setName("UpdatedProductName");
        throw new RuntimeException("심심풀이 예외");
    }

    @Transactional
    public void test(ProductCreateRequest request) {
        ProductResponse productResponse = saveProduct(request);
        updateProduct(productResponse.id());
    }
}
