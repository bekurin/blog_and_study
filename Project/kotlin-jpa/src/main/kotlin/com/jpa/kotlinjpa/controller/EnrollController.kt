package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.EnrollDto
import com.jpa.kotlinjpa.controller.dto.EnrollResponseDto
import com.jpa.kotlinjpa.facade.EnrollFacade
import com.jpa.kotlinjpa.repository.EnrollRepository
import com.jpa.kotlinjpa.sevice.EnrollService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class EnrollController(
        private val enrollFacade: EnrollFacade
) {

    @PostMapping("/enrolls")
    fun enroll(
            @RequestBody @Valid dto: EnrollDto
    ): EnrollResponseDto {
        return EnrollResponseDto(enrollFacade.enroll(dto))
    }
}
