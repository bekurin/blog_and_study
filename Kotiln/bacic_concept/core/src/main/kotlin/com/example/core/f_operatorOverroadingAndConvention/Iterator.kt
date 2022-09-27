package com.example.core.f_operatorOverroadingAndConvention

import java.time.LocalDate

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
        object : Iterator<LocalDate> {
            var current = start

            override fun hasNext() =
                    current <= endInclusive

            override fun next() = current.apply {
                current = plusDays(1)
            }
        }

/**
 * iterator 함수를 확장 함수로 정의할 수 있다.
 * iterator 를 정의하면 for 문에서 원하는 동작을 실시 할 수 있다.
 */
fun main() {
    val newYear = LocalDate.ofYearDay(2023, 1)
    val daysOff = newYear.minusDays(1)..newYear

    for (dayOff in daysOff) {
        println("dayOff = $dayOff")
    }
}