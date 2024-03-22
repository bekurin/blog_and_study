package com.example.countingdailyvisitors.v2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2")
class DailyVisitControllerV2(
    private val dailyVisitServiceV2: DailyVisitServiceV2
) {
    @GetMapping("/visits/{userId}")
    fun visit(
        @PathVariable userId: Long
    ) {
        return dailyVisitServiceV2.visit(userId)
    }

    @GetMapping("/page-visits")
    fun getPageVisitor(): Int {
        return dailyVisitServiceV2.getPageVisitor()
    }

    @GetMapping("/unique-visits")
    fun getUniqueVisitor(): Long {
        return dailyVisitServiceV2.getUniqueVisitor()
    }
}
