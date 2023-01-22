package com.example.authentication.entity

import jakarta.persistence.*

@Entity
class Users(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val username: String = "",

    @Column(nullable = false)
    var password: String = "",
)
