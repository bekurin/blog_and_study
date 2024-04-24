package core.paymentservice.repository

import core.paymentservice.domain.Product
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ProductRepository : ReactiveCrudRepository<Product, Int>
