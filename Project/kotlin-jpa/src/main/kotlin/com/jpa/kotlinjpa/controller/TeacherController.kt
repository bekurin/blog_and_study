package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.HireDto
import com.jpa.kotlinjpa.controller.dto.TeacherResponseDto
import com.jpa.kotlinjpa.sevice.TeacherService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class TeacherController(
        private val teacherService: TeacherService
) {
    @GetMapping("/teachers/{id}")
    fun findById(
            @PathVariable id: Long
    ): TeacherResponseDto {
        return teacherService.findByIdOrThrow(id)
    }

    @PostMapping("/teachers")
    fun signIn(
            @RequestBody dto: HireDto
    ): TeacherResponseDto {
        return teacherService.hire(dto)
    }
}
