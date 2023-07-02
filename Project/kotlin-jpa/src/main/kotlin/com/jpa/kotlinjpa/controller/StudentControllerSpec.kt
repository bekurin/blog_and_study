package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.StudentResponseDto
import com.jpa.kotlinjpa.controller.dto.StudentSignInDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody

interface StudentControllerSpec {
    @Operation(summary = "회원 생성", description = "이름, 이메일, 핸드폰 번호를 사용하여 회원 정보를 저장한다.")
    @RequestBody(
            description = "회원 생성 요청", required = true, content = [
                Content(schema = Schema(implementation = StudentSignInDto::class))
            ]
    )
    fun create(
            dto: StudentSignInDto
    ): StudentResponseDto

    @Operation(summary = "회원 단건 조회", description = "회원 ID를 사용하여 회원 정보를 조회한다.")
    @Parameter(name = "id", description = "회원 ID", required = true)
    fun findById(
            id: Long
    ): StudentResponseDto

    @Operation(summary = "회원 리스트 조회", description = "저장된 모든 회원 정보를 조회한다.")
    fun findAll(): List<StudentResponseDto>
}
