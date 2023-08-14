package com.example.validation.controller

import com.example.validation.controller.dto.SpringValidationRequest
import com.example.validation.exception.ValidatorException
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Range
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/spring-validation/v2")
class SpringValidationControllerV2 {
    /**
     * bindingResult는 RequestBody, RequestPart, ModelAttribute 어노테이션에서만 사용할 수 있고,
     * 해당 객체 바로 뒤에 선언해줘야한다.
     * 즉, Pathvariable, RequestParam에 대한 검증을 잡아내기 위해서는 ExceptionHandler를 정의해야한다.
     *
     * 그렇다면 2가지 방법을 모두 사용하는 것보다 둘 다 ExceptionHandler로 예외 핸들링을 하는 것이 좋지 않을까? 생각이 든다.
     */
    @GetMapping("/path-variable/{id}")
    fun validatePathVariable(
        @PathVariable @Min(1, message = "id는 1이상이어야 합니다") id: Long,
        @RequestParam @Range(min = 10, max = 1000) range: Int
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @GetMapping("/request-params")
    fun validateRequestParams(
        @RequestParam @Min(1, message = "id는 1이상이어야 합니다") id: Long
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity
            .ok()
            .body(mapOf("id" to id))
    }

    @PostMapping("/request-body")
    fun validateRequestBody(
        @RequestBody @Valid request: SpringValidationRequest,
        bindingResult: BindingResult
    ): ResponseEntity<Map<String, SpringValidationRequest>> {
        if (bindingResult.hasErrors()) {
            throw ValidatorException(bindingResult.allErrors.map { it.defaultMessage }.joinToString())
        }
        return ResponseEntity
            .ok()
            .body(mapOf("body" to request))
    }
}
