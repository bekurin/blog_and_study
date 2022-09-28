package com.example.core.g_hignOrderFunctionAndLambda

import com.example.core.a_kotlinBasic.iterator.printLine

data class SiteVisit(
        val path: String,
        val duration: Double,
        val os: OS
)
enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
        filter(predicate).map(SiteVisit::duration).average()

/**
 * 람다 활용
 * averageWindowsDuration, averageMobileDuration 의 경우 원하는 값을 얻기 위해 추가 코드를 많이 작성해야 한다.
 * averageDurationFor 와 같이 filter 의 조건을 파라미터로 받으면 중복 코드를 제거하고, 읽기 편한 코드를 작성할 수 있다.
 */
fun main() {
    val log = listOf(
            SiteVisit("/", 34.0, OS.WINDOWS),
            SiteVisit("/", 22.9, OS.MAC),
            SiteVisit("/login", 12.0, OS.WINDOWS),
            SiteVisit("/signUp", 8.0, OS.IOS),
            SiteVisit("/", 15.3, OS.ANDROID)
    )

    val averageWindowsDuration = log
            .filter { it.os == OS.WINDOWS }
            .map(SiteVisit::duration)
            .average()
    println("averageWindowsDuration = $averageWindowsDuration")
    printLine()

    val averageMobileDuration = log
            .filter { it.os in setOf(OS.ANDROID, OS.IOS) }
            .map(SiteVisit::duration)
            .average()
    println("averageMobileDuration = $averageMobileDuration")
    printLine()

    println("log.averageDurationFor { it.os == OS.IOS && it.path == \"/signUp\" } = ${log.averageDurationFor { it.os == OS.IOS && it.path == "/signUp" }}")
}