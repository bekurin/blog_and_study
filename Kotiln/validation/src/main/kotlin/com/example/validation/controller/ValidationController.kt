package com.example.validation.controller

import com.example.validation.controller.dto.ValidationTestRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
class ValidationController {

    @GetMapping("/validate/path-variable/{id}")
    fun validatePathVariable(
        @PathVariable @Min(0) id: Long
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @GetMapping("/validate/request-params")
    fun validateRequestParams(
        @RequestParam @Min(1) id: Long
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @PostMapping("/validate/request-body")
    fun validateRequestBody(
        @RequestBody @Valid request: ValidationTestRequest
    ): ResponseEntity<Map<String, ValidationTestRequest>> {
        return ResponseEntity
            .ok()
            .body(mapOf("body" to request))
    }


}
