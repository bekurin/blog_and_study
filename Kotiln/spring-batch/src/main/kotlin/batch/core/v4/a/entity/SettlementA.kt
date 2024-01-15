package batch.core.v4.a.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class SettlementA(
    unitPrice: Long,
    paidType: String,
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
}
