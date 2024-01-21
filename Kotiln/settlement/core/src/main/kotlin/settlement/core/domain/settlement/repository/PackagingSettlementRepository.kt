package settlement.core.domain.settlement.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.settlement.PackagingSettlement

interface PackagingSettlementRepository : JpaRepository<PackagingSettlement, Int>
