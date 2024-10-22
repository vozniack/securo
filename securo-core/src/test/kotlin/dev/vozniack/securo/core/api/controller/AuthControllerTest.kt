package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.LoginRequestDto
import dev.vozniack.securo.core.api.dto.LoginResponseDto
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.mock.mockLoginRequest
import dev.vozniack.securo.core.mock.mockUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

class AuthControllerTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

    @AfterEach
    fun `clean up`() {
        userRepository.deleteAll()
    }

    @Test
    fun `login user`() {
        userRepository.save(mockUser().apply { password = passwordEncoder.encode("Admin123!") })

        val request: LoginRequestDto = mockLoginRequest(password = "Admin123!")

        val response: LoginResponseDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertNotNull(response.token)
    }
}
