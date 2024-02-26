package com.example.api.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
open class Coupon(
    userId: Long
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0
        protected set

    var userId: Long = userId
        protected set
}
