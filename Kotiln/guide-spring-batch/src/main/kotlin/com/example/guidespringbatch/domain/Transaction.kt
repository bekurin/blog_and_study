package com.example.guidespringbatch.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Transaction(
    accountId: Int,
    credit: Float,
    debit: Float
) : BaseEntity() {
    @Column(columnDefinition = "int(11)")
    var accountId: Int = accountId
        protected set

    @Column(columnDefinition = "float(20)")
    var credit: Float = credit
        protected set

    @Column(columnDefinition = "float(20)")
    var debit: Float = debit
        protected set
}
