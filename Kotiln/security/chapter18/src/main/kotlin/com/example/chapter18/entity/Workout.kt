package com.example.chapter18.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Workout(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var username: String,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
) {
    constructor() : this(0L, "default", LocalDateTime.now(), LocalDateTime.now())
}
