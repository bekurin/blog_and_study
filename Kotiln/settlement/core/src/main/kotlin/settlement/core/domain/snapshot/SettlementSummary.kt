package settlement.core.domain.snapshot

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import settlement.core.domain.BaseEntity
import settlement.core.domain.Company
import java.time.YearMonth


@Entity
class SettlementSummary(
    company: Company,
    settlementYearMonth: YearMonth,
    totalUnitPrice: Long,
) : BaseEntity() {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    var company: Company = company
        protected set

    @Column(nullable = false)
    var settlementYearMonth: YearMonth = settlementYearMonth
        protected set

    @Column(nullable = false)
    var totalUnitPrice: Long = totalUnitPrice
        protected set

    @OneToMany(fetch = LAZY, mappedBy = "settlementSummary")
    var settlementSnapshots: MutableList<SettlementSnapshot> = mutableListOf()
        protected set
}
