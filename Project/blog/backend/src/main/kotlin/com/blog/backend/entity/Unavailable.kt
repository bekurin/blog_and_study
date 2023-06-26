package com.blog.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
open class Unavailable(
        startAt: LocalDate,
        endAt: LocalDate
) {
    @Column(nullable = true)
    var starAt: LocalDate = startAt
        protected set

    @Column(nullable = true)
    var endAt: LocalDate = endAt
        protected set

    fun isEndAtAfterStartAt(): Boolean {
        return endAt.isAfter(starAt)
    }
}
