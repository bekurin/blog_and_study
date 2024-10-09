package core.server_side_ui_server.controller

import core.server_side_ui_server.controller.response.ScreenResponse
import core.server_side_ui_server.service.ScreenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ScreenController(
    private val screenService: ScreenService
) {
    @GetMapping("/main")
    fun main(): ScreenResponse {
        return screenService.main()
    }

    @GetMapping("/sign-in")
    fun signIn(): ScreenResponse {
        return screenService.signIn()
    }
}
