package com.example.guidespringbatch.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class CustomerAccount(
    customerId: Int,
    accountId: Int,
) : BaseEntity() {
    @Column(columnDefinition = "int(11)")
    var customerId: Int = customerId
        protected set

    @Column(columnDefinition = "int(11)")
    var accountId: Int = accountId
        protected set
}
