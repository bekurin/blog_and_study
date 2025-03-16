package core.profiling.service.v1;

import core.profiling.model.ProductModel;
import core.profiling.model.PurchaseModel;
import core.profiling.repository.v1.ProductV1Repository;
import core.profiling.repository.v1.PurchaseV1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseV1Service {
    private final ProductV1Repository productRepository;
    private final PurchaseV1Repository purchaseRepository;

    public Set<String> getProductNamesForPurchases() {
        Set<String> productNames = new HashSet<>();
        List<PurchaseModel> purchases = purchaseRepository.findAll();
        for (PurchaseModel p : purchases) {
            ProductModel product = productRepository.findProduct(p.getProduct());
            productNames.add(product.getName());
        }
        return productNames;
    }
}
