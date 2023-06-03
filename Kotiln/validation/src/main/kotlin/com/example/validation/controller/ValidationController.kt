package com.example.validation.controller

import com.example.validation.controller.dto.ValidationRequest
import com.example.validation.exception.ClientBadRequestException
import org.springframework.http.ResponseEntity
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/validate")
class ValidationController {
    val logger: Logger = LoggerFactory.getLogger(ValidationController::class.java)

    @GetMapping("/path-variable/{id}")
    fun validatePathVariable(
        @PathVariable id: Long,
    ): ResponseEntity<Map<String, Long>> {
        validateId(id)
        logger.info("path-variable id={}", id)
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @GetMapping("/request-params")
    fun validateRequestParams(
        @RequestParam id: Long,
    ): ResponseEntity<Map<String, Long>> {
        validateId(id)
        logger.info("request-params id={}", id)
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @PostMapping("/request-body")
    fun validateRequestBody(
        @RequestBody request: ValidationRequest,
    ): ResponseEntity<Map<String, ValidationRequest>> {
        validateValidationTestRequest(request)
        logger.info("request-body request={}", request)
        return ResponseEntity
            .ok()
            .body(mapOf("body" to request))
    }

    private fun validateId(id: Long) {
        if (id < 1) {
            throw ClientBadRequestException("id는 1 미만일 수 없습니다")
        }
    }

    private fun validateValidationTestRequest(request: ValidationRequest) {
        if (request.message.isBlank() && request.id < 1) {
            throw ClientBadRequestException("message는 빈칸일 수 없습니다, id는 1미만일 수 없습니다.")
        } else if (request.message.isBlank()) {
            throw ClientBadRequestException("message는 빈칸일 수 없습니다.")
        } else if (request.id < 1) {
            throw ClientBadRequestException("id는 1 미만일 수 없습니다")
        }
        return
    }
}
