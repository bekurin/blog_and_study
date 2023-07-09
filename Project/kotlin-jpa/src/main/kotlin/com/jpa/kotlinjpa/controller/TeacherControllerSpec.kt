package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.controller.dto.HireDto
import com.jpa.kotlinjpa.controller.dto.TeacherResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody

interface TeacherControllerSpec {

    @Operation(summary = "선생님 단건 조회", description = "선생님 ID를 사용하여 선생님 정보를 조회한다")
    @Parameter(name = "id", description = "선생님 ID", required = true)
    fun findById(
            id: Long
    ): TeacherResponseDto

    @Operation(summary = "선생님 고용", description = "이름을 사용하여 선생님 정보를 저장한다")
    @RequestBody(
            description = "선생님 생성 요청", required = true, content = [
        Content(schema = Schema(implementation = HireDto::class))
    ]
    )
    fun hire(
            dto: HireDto
    ): TeacherResponseDto
}
