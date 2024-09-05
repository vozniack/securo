package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.LoginRequest
import dev.vozniack.securo.core.api.dto.LoginResponse
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.internal.exception.UnauthorizedException
import dev.vozniack.securo.core.mock.mockLoginRequest
import dev.vozniack.securo.core.mock.mockUser
import kotlin.test.Test
import kotlin.test.assertNotNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceTest @Autowired constructor(
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AbstractUnitTest() {

    @AfterEach
    fun cleanUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `login user`() {
        userRepository.save(mockUser().apply { password = passwordEncoder.encode("Admin123!") })
        val request: LoginRequest = mockLoginRequest(password = "Admin123!")

        val response: LoginResponse = authService.login(request)
        assertNotNull(response.token)
    }

    @Test
    fun `login with incorrect password`() {
        userRepository.save(mockUser().apply { password = passwordEncoder.encode("Admin123!") })
        val request: LoginRequest = mockLoginRequest(password = "Blabla123!")

        assertThrows<UnauthorizedException> {
            authService.login(request)
        }
    }
}
