package core.paymentservice.util

import java.util.*

object Constant {
    const val TOSS_PAYMENT = "toss-payment"
}

object IdempotencyCreator {
    fun create(data: Any): String {
        return UUID.nameUUIDFromBytes(data.toString().toByteArray()).toString()
    }
}
