package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.HireDto
import com.jpa.kotlinjpa.controller.dto.TeacherResponseDto
import com.jpa.kotlinjpa.sevice.TeacherService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/v1")
class TeacherController(
        private val teacherService: TeacherService
) : TeacherControllerSpec {
    @GetMapping("/teachers/{id}")
    override fun findById(
            @PathVariable @Min(1) id: Long
    ): TeacherResponseDto {
        return teacherService.findByIdOrThrow(id)
    }

    @PostMapping("/teachers")
    override fun hire(
            @RequestBody @Valid dto: HireDto
    ): TeacherResponseDto {
        return teacherService.hire(dto)
    }
}
