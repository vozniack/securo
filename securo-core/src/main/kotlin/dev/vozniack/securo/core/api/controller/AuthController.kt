package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.LoginRequest
import dev.vozniack.securo.core.api.dto.LoginResponse
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
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        logger.debug { "Logging in user ${request.email}" }

        return authService.login(request)
    }

    companion object : KLogging()
}
