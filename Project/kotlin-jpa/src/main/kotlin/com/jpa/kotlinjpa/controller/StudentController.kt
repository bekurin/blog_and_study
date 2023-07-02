package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.StudentResponseDto
import com.jpa.kotlinjpa.controller.dto.StudentSignInDto
import com.jpa.kotlinjpa.sevice.StudentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class StudentController(
        private val studentService: StudentService
) : StudentControllerSpec {
    @PostMapping("/member")
    override fun create(
            @RequestBody dto: StudentSignInDto
    ): StudentResponseDto {
        return studentService.signIn(dto)
    }

    @GetMapping("/members/{id}")
    override fun findById(
            @PathVariable id: Long
    ): StudentResponseDto {
        return studentService.findMemberById(id)
    }

    @GetMapping("/members")
    override fun findAll(): List<StudentResponseDto> {
        return studentService.findAll()
    }
}
