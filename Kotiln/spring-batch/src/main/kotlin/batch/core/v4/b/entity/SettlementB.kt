package batch.core.v4.b.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class SettlementB(
    unitPrice: Long,
    paidType: String,
    isMigrated: Boolean
) {
    @Id
    @GeneratedValue
    var id: Long = 0
        protected set

    @Column(nullable = false)
    var unitPrice: Long = unitPrice
        protected set

    @Column(nullable = false)
    var paidType: String = paidType
        protected set

    @Column(nullable = false)
    var isMigrated: Boolean = isMigrated
        protected set
}
