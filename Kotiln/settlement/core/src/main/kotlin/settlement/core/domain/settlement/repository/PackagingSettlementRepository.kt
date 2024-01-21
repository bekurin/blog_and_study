package settlement.core.domain.settlement.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.settlement.DeliverySettlement
import settlement.core.domain.settlement.PackagingSettlement
import java.time.LocalDate

interface PackagingSettlementRepository : JpaRepository<PackagingSettlement, Int> {
    fun findByCompanyIdAndPackagingDateBetween(
        companyId: Int,
        packagingStartDate: LocalDate,
        packagingEndDate: LocalDate
    ): List<PackagingSettlement>
}
