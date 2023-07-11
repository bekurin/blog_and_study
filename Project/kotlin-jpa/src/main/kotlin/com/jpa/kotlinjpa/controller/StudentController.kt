package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.StudentResponseDto
import com.jpa.kotlinjpa.controller.dto.StudentSignInDto
import com.jpa.kotlinjpa.sevice.StudentService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/v1")
class StudentController(
        private val studentService: StudentService
) : StudentControllerSpec {
    @PostMapping("/member")
    override fun create(
            @RequestBody @Valid dto: StudentSignInDto
    ): StudentResponseDto {
        return StudentResponseDto(studentService.signIn(dto))
    }

    @GetMapping("/members/{id}")
    override fun findById(
            @PathVariable @Min(1) id: Long
    ): StudentResponseDto {
        return StudentResponseDto(studentService.findMemberById(id))
    }

    @GetMapping("/members")
    override fun findAll(): List<StudentResponseDto> {
        return studentService
                .findAll()
                .map { StudentResponseDto(it) }
    }
}
