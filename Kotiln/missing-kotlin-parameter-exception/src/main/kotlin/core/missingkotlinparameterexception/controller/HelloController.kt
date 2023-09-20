package core.missingkotlinparameterexception.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @PostMapping("/hello")
    fun hello(
        @RequestBody request: HelloRequest
    ): ResponseEntity<HelloRequest> {
        return ResponseEntity.ok(request)
    }
}

data class HelloRequest(
    val name: String
)
