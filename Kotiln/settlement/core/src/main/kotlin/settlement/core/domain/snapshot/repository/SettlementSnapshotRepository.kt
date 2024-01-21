package settlement.core.domain.snapshot.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.snapshot.SettlementSnapshot

interface SettlementSnapshotRepository : JpaRepository<SettlementSnapshot, Int>
