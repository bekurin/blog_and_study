package troubleshooting.jpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import troubleshooting.jpa.controller.request.ProductCreateRequest;
import troubleshooting.jpa.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void test() {
        ProductCreateRequest request = new ProductCreateRequest("name");
        productService.test(request);
    }

}