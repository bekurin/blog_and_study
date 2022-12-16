package chapter03

import java.time.LocalDateTime

class Listing6 {
    class Delivery(
        val date: LocalDateTime,
    )

    class DeliveryService {
        fun isDeliveryValid(delivery: Delivery): Boolean {
            return delivery.date >= LocalDateTime.now().plusDays(2L)
        }
    }
}