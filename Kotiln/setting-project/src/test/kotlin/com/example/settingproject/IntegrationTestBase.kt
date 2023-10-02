package com.example.settingproject

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IntegrationTestBase {

    protected lateinit var mvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUpMvc() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(print())
            .build()
    }
}
