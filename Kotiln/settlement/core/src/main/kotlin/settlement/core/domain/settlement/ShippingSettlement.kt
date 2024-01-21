package settlement.core.domain.settlement

import jakarta.persistence.Column
import jakarta.persistence.Entity
import settlement.core.domain.BaseEntity
import java.time.LocalDate

@Entity
class ShippingSettlement(
    companyId: Int,
    shippingDate: LocalDate,
    unitPrice: Int,
) : BaseEntity() {
    @Column(nullable = false)
    var companyId: Int = companyId
        protected set

    @Column(nullable = false)
    var shippingDate: LocalDate = shippingDate
        protected set

    @Column(nullable = false)
    var unitPrice: Int = unitPrice
        protected set
}
