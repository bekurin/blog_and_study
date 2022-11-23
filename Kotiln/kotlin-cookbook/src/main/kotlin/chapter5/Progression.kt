package chapter5

import java.time.LocalDate

fun main() {
    val startDate = LocalDate.now()
    val midDate = startDate.plusDays(5)
    val endDate = startDate.plusDays(7)

    val dateRange = startDate..endDate
    println("startDate in dateRange = ${startDate in dateRange}")
    println("midDate in dateRange = ${midDate in dateRange}")
    println("endDate in dateRange = ${endDate in dateRange}")
    println("endDate.plusDays(1) in dateRange = ${endDate.plusDays(1) in dateRange}")
}