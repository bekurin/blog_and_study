package troubleshooting.jpa.controller.request;

import troubleshooting.jpa.entity.Product;

public record ProductCreateRequest(
    String name
) {
    public Product toProduct() {
        return new Product(name);
    }
}
