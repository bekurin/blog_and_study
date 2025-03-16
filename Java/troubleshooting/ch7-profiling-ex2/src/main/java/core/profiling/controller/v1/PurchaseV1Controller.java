package core.profiling.controller.v1;

import core.profiling.service.v1.PurchaseV1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PurchaseV1Controller {
    private final PurchaseV1Service purchaseService;

    @GetMapping("/products")
    public Set<String> findPurchasedProductNames() {
        return purchaseService.getProductNamesForPurchases();
    }
}
