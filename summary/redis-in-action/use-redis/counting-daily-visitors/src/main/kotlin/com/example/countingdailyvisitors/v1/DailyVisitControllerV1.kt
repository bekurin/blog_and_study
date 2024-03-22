package com.example.countingdailyvisitors.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class DailyVisitControllerV1(
    private val dailyVisitServiceV1: DailyVisitServiceV1
) {
    @GetMapping("/visits/{userId}")
    fun visit(
        @PathVariable userId: Long
    ) {
        return dailyVisitServiceV1.visit(userId)
    }

    @GetMapping("/page-visits")
    fun getPageVisitor(): Int {
        return dailyVisitServiceV1.getPageVisitor()
    }

    @GetMapping("/unique-visits")
    fun getUniqueVisitor(): Long {
        return dailyVisitServiceV1.getUniqueVisitor()
    }
}
