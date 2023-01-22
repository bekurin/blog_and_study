package com.example.chapter11.entity

import jakarta.persistence.*

@Entity
class Otp(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false)
    var code: String = "",
)