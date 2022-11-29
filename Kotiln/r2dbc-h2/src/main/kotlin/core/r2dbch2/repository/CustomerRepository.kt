package core.r2dbch2.repository

import core.r2dbch2.domain.Customer
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface CustomerRepository : ReactiveCrudRepository<Customer, Long> {

    @Query("SELECT * FROM customer WHERE last_name = :lastname")
    fun findByLastName(lastName: String): Flux<Customer>
}