package remote.debug.controller;

import lombok.RequiredArgsConstructor;
import remote.debug.model.dto.TotalCostResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import remote.debug.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/total-costs")
    public TotalCostResponse getTotalCosts() {
        return productService.getTotalCosts();
    }
}
