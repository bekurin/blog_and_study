package com.example.guidespringbatch.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
class Account(
    balance: Float,
    lastStatementDate: LocalDate
): BaseEntity() {
    @Column(columnDefinition = "float(20)")
    var balance: Float = balance
        protected set

    @Column(columnDefinition = "date")
    var lastStatementDate: LocalDate = lastStatementDate
        protected set
}
