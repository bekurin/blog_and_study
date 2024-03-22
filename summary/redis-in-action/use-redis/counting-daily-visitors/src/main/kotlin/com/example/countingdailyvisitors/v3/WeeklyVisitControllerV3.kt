package com.example.countingdailyvisitors.v1

import com.example.countingdailyvisitors.v3.WeeklyVisitServiceV3
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/v3")
class WeeklyVisitControllerV3(
    private val weeklyVisitServiceV3: WeeklyVisitServiceV3
) {
    @GetMapping("/visits/{userId}")
    fun visit(
        @PathVariable userId: Long
    ) {
        return weeklyVisitServiceV3.visit(userId, LocalDate.now())
    }

    @GetMapping("/page-visits")
    fun getPageVisitor(): Long {
        val now = LocalDate.now()
        return weeklyVisitServiceV3.getWeeklyPageVisitor(now)
    }

    @GetMapping("/unique-visits")
    fun getUniqueVisitor(): Long {
        val now = LocalDate.now()
        return weeklyVisitServiceV3.getWeeklyUniqueVisitCount(now)
    }
}
