package core.serverdrivenui.adapter.`in`.web

import core.serverdrivenui.application.port.`in`.GetLayoutUseCase
import core.serverdrivenui.domain.model.Layout
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/layout")
class LayoutController(
    private val getLayoutUseCase: GetLayoutUseCase,
) {

    @GetMapping
    fun getLayout(): Layout = getLayoutUseCase.getLayout()
}
