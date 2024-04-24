package core.paymentservice.repository

import core.paymentservice.domain.PaymentOrder
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PaymentOrderRepository : ReactiveCrudRepository<PaymentOrder, Int>
