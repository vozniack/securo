package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.LoginRequestDto
import dev.vozniack.securo.core.api.dto.LoginResponseDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.internal.exception.UnauthorizedException
import dev.vozniack.securo.core.service.extension.collectCodes
import dev.vozniack.securo.core.service.extension.collectPrivileges
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${securo.security.jwt.secret}") private val jwtSecret: String,
    @Value("\${securo.security.jwt.expiration}") private val jwtExpiration: String
) {

    @Transactional
    fun login(request: LoginRequestDto): LoginResponseDto = userRepository.findByEmail(request.email)
        ?.takeIf { passwordEncoder.matches(request.password, it.password) }
        ?.let { LoginResponseDto(token = buildToken(it)) }
        ?: throw UnauthorizedException("User ${request.email} has not been authorized")

    private fun buildToken(user: User): String = Jwts.builder()
        .setSubject(user.email)
        .addClaims(mapOf(Pair("roles", user.roles.map { "${it.system.code}_${it.code}" })) as Map<String, Any>?)
        .addClaims(mapOf(Pair("privileges", collectPrivileges(user).collectCodes())) as Map<String, Any>?)
        .signWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8)))
        .setExpiration(Date(Date().time + jwtExpiration.toInt()))
        .compact()
}
