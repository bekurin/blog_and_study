package com.example.hello

import com.example.hello.config.IntegrationTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HelloApplicationTests : IntegrationTestBase() {
    @Test
    fun contextLoads() {
        val one = 1
        assertThat(one).isEqualTo(1)
    }
}
