package settlement.core.domain.settlement.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.settlement.DeliverySettlement

interface DeliverySettlementRepository : JpaRepository<DeliverySettlement, Int> {
    fun findByCompanyId(companyId: Int): List<DeliverySettlement>
}
