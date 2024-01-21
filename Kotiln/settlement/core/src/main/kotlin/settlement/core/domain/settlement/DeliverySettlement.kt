package settlement.core.domain.settlement

import jakarta.persistence.Column
import jakarta.persistence.Entity
import settlement.core.domain.BaseEntity
import java.time.LocalDate

@Entity
class DeliverySettlement(
    companyId: Int,
    deliveryDate: LocalDate,
    unitPrice: Int,
) : BaseEntity() {
    @Column(nullable = false)
    var companyId: Int = companyId
        protected set

    @Column(nullable = false)
    var deliveryDate: LocalDate = deliveryDate
        protected set

    @Column(nullable = false)
    var unitPrice: Int = unitPrice
        protected set
}
