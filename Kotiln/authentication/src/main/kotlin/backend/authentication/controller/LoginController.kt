package backend.authentication.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {

    @GetMapping("/login")
    fun login(): String {
        return "login/login"
    }

    @GetMapping("/logout")
    fun logout(): String {
        return "login/logout"
    }
}
