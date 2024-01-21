package settlement.core.domain.snapshot.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.snapshot.SettlementSummary

interface SettlementSummaryRepository : JpaRepository<SettlementSummary, Int>
