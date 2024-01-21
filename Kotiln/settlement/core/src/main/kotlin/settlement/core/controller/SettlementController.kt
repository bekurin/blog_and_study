package settlement.core.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import settlement.core.service.SettlementService
import java.time.YearMonth


@RestController
@RequestMapping("/v1")
class SettlementController(
    private val settlementService: SettlementService
) {
    @PostMapping("/settlements")
    @ResponseStatus(CREATED)
    fun createSettlements(
        @RequestBody request: SettlementCreationRequest
    ) {
        settlementService.settlements(request.settlementYearMonth)
    }
}

data class SettlementCreationRequest(
    val settlementYearMonth: YearMonth
)

class SettlementJobParameter {
    private var settlementYearMonth: YearMonth? = null

    @Value("#{jobParameters[settlementYearMonth]}")
    fun setSettlementYearMonth(settlementYearMonth: String): YearMonth? {
        return YearMonth.parse(settlementYearMonth)
    }
}
