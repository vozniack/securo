package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.LoginRequestDto
import dev.vozniack.securo.core.api.dto.LoginResponseDto
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.internal.exception.UnauthorizedException
import dev.vozniack.securo.core.mock.mockLoginRequest
import dev.vozniack.securo.core.mock.mockUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceTest @Autowired constructor(
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        userRepository.deleteAll()
    }

    @Test
    fun `login user`() {
        userRepository.save(mockUser().apply { password = passwordEncoder.encode("Admin123!") })
        val request: LoginRequestDto = mockLoginRequest(password = "Admin123!")

        val response: LoginResponseDto = authService.login(request)
        assertNotNull(response.token)
    }

    @Test
    fun `login with incorrect password`() {
        userRepository.save(mockUser().apply { password = passwordEncoder.encode("Admin123!") })
        val request: LoginRequestDto = mockLoginRequest(password = "Blabla123!")

        assertThrows<UnauthorizedException> {
            authService.login(request)
        }
    }
}
