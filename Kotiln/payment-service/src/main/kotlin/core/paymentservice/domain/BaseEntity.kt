package core.paymentservice.domain

import org.springframework.data.annotation.Id

open class BaseEntity : TimestampBaseEntity() {
    @Id
    var id: Int = 0
        protected set
}
