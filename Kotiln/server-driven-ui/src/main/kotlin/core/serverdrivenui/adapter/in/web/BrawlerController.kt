package core.serverdrivenui.adapter.`in`.web

import core.serverdrivenui.application.port.`in`.GetBrawlerUseCase
import core.serverdrivenui.domain.model.Brawler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brawlers")
class BrawlerController(
    private val getBrawlerUseCase: GetBrawlerUseCase,
) {

    @GetMapping
    fun getBrawlers(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) tier: String?,
    ): List<Brawler> = getBrawlerUseCase.getAllBrawlers(name, tier)

    @GetMapping("/{brawlerId}")
    fun getBrawlerById(@PathVariable brawlerId: String): ResponseEntity<Brawler> {
        val brawler = getBrawlerUseCase.getBrawlerById(brawlerId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(brawler)
    }
}
