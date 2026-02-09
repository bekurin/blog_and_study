package core.serverdrivenui.adapter.`in`.web

import core.serverdrivenui.application.port.`in`.GetPageUseCase
import core.serverdrivenui.domain.model.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/pages")
class PageController(
    private val getPageUseCase: GetPageUseCase,
) {

    @GetMapping("/{pageKey}")
    fun getPage(@PathVariable pageKey: String): ResponseEntity<Page> {
        val page = getPageUseCase.getPage(pageKey)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(page)
    }
}
