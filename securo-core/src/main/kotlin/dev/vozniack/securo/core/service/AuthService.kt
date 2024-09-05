package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.LoginRequest
import dev.vozniack.securo.core.api.dto.LoginResponse
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.internal.exception.UnauthorizedException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${securo.security.jwt.secret}") private val jwtSecret: String,
    @Value("\${securo.security.jwt.expiration}") private val jwtExpiration: String
) {

    fun login(loginRequest: LoginRequest): LoginResponse = userRepository.findByEmail(loginRequest.email)
        ?.takeIf { passwordEncoder.matches(loginRequest.password, it.password) }
        ?.let { LoginResponse(token = buildToken(it)) } ?: throw UnauthorizedException("User has not been authorized")

    private fun buildToken(user: User): String = Jwts.builder()
        .setSubject(user.email)
        .signWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8)))
        .setExpiration(Date(Date().time + jwtExpiration.toInt())) // 12 hours
        .compact()
}
