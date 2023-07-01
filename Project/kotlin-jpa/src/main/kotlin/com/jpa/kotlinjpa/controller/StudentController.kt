package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.StudentResponseDto
import com.jpa.kotlinjpa.controller.dto.StudentSignInDto
import com.jpa.kotlinjpa.sevice.StudentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class StudentController(
        private val studentService: StudentService
) {
    @PostMapping("/member")
    fun create(
            @RequestBody dto: StudentSignInDto
    ): StudentResponseDto {
        return studentService.signIn(dto)
    }

    @GetMapping("/members/{id}")
    fun findById(
            @PathVariable id: Long
    ): StudentResponseDto {
        return studentService.findMemberById(id)
    }

    @GetMapping("/members")
    fun findAll(): List<StudentResponseDto> {
        return studentService.findAll()
    }
}
