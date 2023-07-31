package com.jpa.kotlinjpa.controller

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
class HelloController {

    data class HelloDto(
            var name: String?
    ) {
        init {
            if (name.isNullOrBlank()) {
                this.name = null
            }
        }
    }

    @GetMapping("/hello")
    fun helloRequestParam(
            @RequestParam name: String?
    ): Map<String, String> {
        return mapOf("message" to "hello $name")
    }

    @GetMapping("/hello/{name}")
    fun helloPathVariable(
            @PathVariable name: String?
    ): Map<String, String> {
        return mapOf("message" to "hello $name")
    }

    @PostMapping("/hello")
    fun helloRequestBody(
            @RequestBody dto: HelloDto
    ): Map<String, String> {
        return mapOf("message" to "hello ${dto.name}")
    }
}
