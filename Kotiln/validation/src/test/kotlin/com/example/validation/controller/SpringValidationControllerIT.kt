package com.example.validation.controller

import com.example.validation.controller.dto.SpringValidationRequest
import com.example.validation.controller.dto.ValidationRequest
import com.example.validation.support.IntegrationTestBase
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import kotlin.random.Random
import kotlin.random.nextLong

class SpringValidationControllerIT : IntegrationTestBase() {

    @Nested
    inner class `path variable 유효성 검증을 할 때` {
        private val validId = Random.nextLong(LongRange(1L, 100L))
        private val invalidId = Random.nextLong(LongRange(-100L, 0L))

        @Nested
        inner class `ID 값이 1 미만이라면` {
            @Test
            fun `400 에러가 발생한다`() {
                val result = mvc.perform(
                    get("/validate/path-variable/$invalidId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                result.andExpect(status().isBadRequest)
            }
        }

        @Nested
        inner class `유효한 파라미터가 입력되면` {

            @Test
            fun `입력받은 ID값을 응답한다`() {
                val result = mvc.perform(
                    get("/validate/path-variable/$validId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                result.andExpect(status().isOk)
                val response = jacksonObjectMapper().readValue(
                    result.andReturn().response.contentAsString,
                    object : TypeReference<Map<String, Long>>() {})
                assertThat(response["id"]).isEqualTo(validId)
            }
        }
    }

    @Nested
    inner class `request param 유효성 검증을 할 때` {
        private val validId = Random.nextLong(LongRange(1L, 100L))
        private val invalidId = Random.nextLong(LongRange(-100L, 0L))

        @Nested
        inner class `ID 값이 1 미만이라면` {
            @Test
            fun `400 에러가 발생한다`() {
                val result = mvc.perform(
                    get("/validate/request-params?id=$invalidId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                result.andExpect(status().isBadRequest)
            }
        }

        @Nested
        inner class `유효한 파라미터가 입력되면` {

            @Test
            fun `입력받은 ID값을 응답한다`() {
                val result = mvc.perform(
                    get("/validate/request-params?id=$validId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                result.andExpect(status().isOk)
                val response = jacksonObjectMapper().readValue(
                    result.andReturn().response.contentAsString,
                    object : TypeReference<Map<String, Long>>() {})
                assertThat(response["id"]).isEqualTo(validId)
            }
        }
    }

    @Nested
    inner class `request body 유효성 검증을 할 때` {

        @Nested
        inner class `ID가 1 미만이거나 message가 빈칸이라면` {

            @ParameterizedTest
            @CsvSource(
                value = [
                    "-1, not empty",
                    "1, ''"
                ]
            )
            fun `400 에러가 발생한다`(id: Long, message: String) {
                val givenRequest = SpringValidationRequest(id, message)

                // when
                val result = mvc.perform(
                    post("/validate/request-body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(givenRequest))
                )

                // then
                result.andExpect(status().isBadRequest)
            }
        }

        @Nested
        inner class `유효한 파라미터가 입력되면` {

            @Test
            fun `입력받은 body를 응답한다`() {
                val (givenId, givenMessage) = Random.nextLong(1000L) to UUID.randomUUID().toString()
                val givenRequest = SpringValidationRequest(givenId, givenMessage)

                // when
                val result = mvc.perform(
                    post("/validate/request-body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(givenRequest))
                )

                // then
                val response = jacksonObjectMapper().readValue(
                    result.andReturn().response.contentAsString,
                    object : TypeReference<Map<String, ValidationRequest>>() {})
                SoftAssertions.assertSoftly { softly ->
                    softly.assertThat(response["body"]?.id).isEqualTo(givenId)
                    softly.assertThat(response["body"]?.message).isEqualTo(givenMessage)
                }
            }
        }
    }
}
