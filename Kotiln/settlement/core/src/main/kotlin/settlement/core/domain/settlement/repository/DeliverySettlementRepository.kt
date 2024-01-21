package settlement.core.domain.settlement.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.settlement.DeliverySettlement
import java.time.LocalDate

interface DeliverySettlementRepository : JpaRepository<DeliverySettlement, Int> {
    fun findByCompanyIdAndDeliveryDateBetween(companyId: Int, deliveryStartDate: LocalDate, deliveryEndDate: LocalDate): List<DeliverySettlement>
}
