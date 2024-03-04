package com.example.stresstest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ScenarioController {
    @PostMapping("/login")
    fun login(): String {
        Thread.sleep(20)
        return "Login Success"
    }

    @GetMapping("/some-function-one")
    fun someFunctionOne(): String {
        Thread.sleep(30)
        return "Result one"
    }

    @GetMapping("/some-function-two")
    fun someFunctionTwo(): String {
        Thread.sleep(15)
        return "Result two"
    }
}
