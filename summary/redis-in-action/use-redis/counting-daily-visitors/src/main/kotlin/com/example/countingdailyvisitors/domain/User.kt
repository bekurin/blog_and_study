package com.example.countingdailyvisitors.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
open class User(email: String) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L
        protected set

    @Column(nullable = false, length = 64)
    var email: String = email
        protected set
}
