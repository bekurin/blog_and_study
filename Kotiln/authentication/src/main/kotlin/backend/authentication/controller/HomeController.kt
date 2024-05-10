package backend.authentication.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun dashboard(): String {
        return "/dashboard"
    }

    @GetMapping("/user")
    fun user(): String {
        return "/user"
    }

    @GetMapping("/manager")
    fun manager(): String {
        return "/manager"
    }

    @GetMapping("/admin")
    fun admin(): String {
        return "/admin"
    }
}
