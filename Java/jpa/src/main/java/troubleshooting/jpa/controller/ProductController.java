package troubleshooting.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import troubleshooting.jpa.controller.request.ProductCreateRequest;
import troubleshooting.jpa.controller.response.ProductResponse;
import troubleshooting.jpa.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
        @RequestBody ProductCreateRequest request
    ) {
        return productService.saveProduct(request);
    }
}
