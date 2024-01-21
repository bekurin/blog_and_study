package settlement.core.controller

import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/settlements")
class SettlementController(
    private val jobLauncher: JobLauncher
) {
    @PostMapping()
    fun createSettlements() {
        TODO("jobLauncher를 사용하여 정산 batch 실행하기")
    }
}
