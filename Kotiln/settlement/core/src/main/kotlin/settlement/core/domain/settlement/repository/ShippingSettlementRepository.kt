package settlement.core.domain.settlement.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.settlement.ShippingSettlement
import java.time.LocalDate

interface ShippingSettlementRepository : JpaRepository<ShippingSettlement, Int> {
    fun findByCompanyIdAndShippingDateBetween(
        companyId: Int,
        shippingStartDate: LocalDate,
        shippingEndDate: LocalDate
    ): List<ShippingSettlement>
}
