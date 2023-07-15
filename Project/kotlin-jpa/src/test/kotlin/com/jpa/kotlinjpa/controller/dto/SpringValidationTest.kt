package com.jpa.kotlinjpa.controller.dto

import jakarta.validation.Validation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SpringValidationTest {
    private val validatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `id가 0미만이면 에러가 발생한다`() {
        val violations = validator.validate(EnrollDto(-1, -1))
        assertThat(violations).isNotEmpty()
    }
}
