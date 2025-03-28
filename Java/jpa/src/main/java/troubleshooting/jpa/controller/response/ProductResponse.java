package troubleshooting.jpa.controller.response;

import troubleshooting.jpa.entity.Product;

public record ProductResponse(
    Long id,
    String name
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName());
    }
}
