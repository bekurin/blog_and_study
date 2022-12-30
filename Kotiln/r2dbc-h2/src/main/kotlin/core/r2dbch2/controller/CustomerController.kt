package core.r2dbch2.controller

import core.r2dbch2.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping("/balance/{customerId}")
    fun getBalance(
        @PathVariable customerId: String
    ) {
        customerService.getBalanceOrThrow(customerId)
    }
}