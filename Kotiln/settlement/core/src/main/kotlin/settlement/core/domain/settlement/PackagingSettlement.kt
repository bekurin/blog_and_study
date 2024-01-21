package settlement.core.domain.settlement

import jakarta.persistence.Column
import jakarta.persistence.Entity
import settlement.core.domain.BaseEntity
import java.time.LocalDate

@Entity
class PackagingSettlement(
    companyId: Int,
    packagingDate: LocalDate,
    unitPrice: Int,
): BaseEntity() {
    @Column(nullable = false)
    var companyId: Int = companyId
        protected set

    @Column(nullable = false)
    var packagingDate: LocalDate = packagingDate
        protected set

    @Column(nullable = false)
    var unitPrice: Int = unitPrice
        protected set
}
