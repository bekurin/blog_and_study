package core.paymentservice.repository

import core.paymentservice.domain.PaymentEvent
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PaymentEventRepository : ReactiveCrudRepository<PaymentEvent, Long>
