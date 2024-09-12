package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.LoginRequestDto
import dev.vozniack.securo.core.api.dto.LoginResponseDto
import dev.vozniack.securo.core.internal.logging.KLogging
import dev.vozniack.securo.core.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping
    fun login(@RequestBody request: LoginRequestDto): LoginResponseDto {
        logger.debug { "Logging in user ${request.email}" }

        return authService.login(request)
    }

    companion object : KLogging()
}
