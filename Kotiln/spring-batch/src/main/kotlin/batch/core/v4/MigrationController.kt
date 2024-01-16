package batch.core.v4

import org.springframework.batch.core.JobExecution
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class MigrationController(
    private val migrationService: MigrationService
) {
    @PostMapping("/migrate")
    fun migrateSettlements(): ResponseEntity<JobExecution> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(migrationService.migrateSettlements())
    }
}
