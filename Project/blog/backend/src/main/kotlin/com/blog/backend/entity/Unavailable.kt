package com.blog.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
open class Unavailable(
        startAt: LocalDate,
        endAt: LocalDate
) {
    init {
        if (startAt.isAfter(endAt)) {
            throw RuntimeException("시작 날짜는 종료 날짜보다 클 수 없습니다")
        }
    }

    @Column(nullable = true)
    var starAt: LocalDate = startAt
        protected set

    @Column(nullable = true)
    var endAt: LocalDate = endAt
        protected set
}
