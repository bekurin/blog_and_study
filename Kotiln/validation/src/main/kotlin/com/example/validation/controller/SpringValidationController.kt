package com.example.validation.controller

import com.example.validation.controller.dto.SpringValidationRequest
import com.example.validation.controller.dto.ValidationRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/spring-validation")
class SpringValidationController {

    @GetMapping("/path-variable/{id}")
    fun validatePathVariable(
        @PathVariable @Min(1, message = "id는 1이상이어야 합니다") id: Long,
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @GetMapping("/request-params")
    fun validateRequestParams(
        @RequestParam @Min(1, message = "id는 1이상이어야 합니다") id: Long,
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @PostMapping("/request-body")
    fun validateRequestBody(
        @RequestBody @Valid request: SpringValidationRequest,
    ): ResponseEntity<Map<String, SpringValidationRequest>> {
        return ResponseEntity
            .ok()
            .body(mapOf("body" to request))
    }
}
