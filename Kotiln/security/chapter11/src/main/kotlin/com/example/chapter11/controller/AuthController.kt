package com.example.chapter11.controller

import com.example.chapter11.entity.Otp
import com.example.chapter11.entity.Users
import com.example.chapter11.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

class userDto(
    val username: String,
    val password: String
) {
    fun toEntity(): Users {
        return Users(username = username, password = password)
    }
}

@RestController
class AuthController(
    private val userService: UserService,
) {
    @PostMapping("/user/add")
    fun addUser(@RequestBody users: Users) {
        userService.addUser(users)
    }

    @PostMapping("/user/auth")
    fun auth(@RequestBody users: userDto) {
        println("user = ${users.password}")
        userService.auth(users.toEntity())
    }

    @PostMapping("/otp/check")
    fun check(@RequestBody otp: Otp, response: HttpServletResponse) {
        if (userService.check(otp)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            response.status = HttpServletResponse.SC_FORBIDDEN
        }
    }
}