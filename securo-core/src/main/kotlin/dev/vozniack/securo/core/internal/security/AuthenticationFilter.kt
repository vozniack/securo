package dev.vozniack.securo.core.internal.security

import dev.vozniack.securo.core.internal.exception.UnauthorizedException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.nio.charset.StandardCharsets
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

class AuthenticationFilter(private val jwtSecret: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        takeIf { !(request.requestURL.contains("api/v1/auth") || request.requestURL.contains("actuator/health")) }?.let {
            val token = parseJwtToken(request) ?: throw UnauthorizedException("Token is not present")
            val loggedUser = buildLoggedUser(token) ?: throw UnauthorizedException("Token is not valid")

            SecurityContextHolder.getContext().authentication = loggedUser
        }.also { filterChain.doFilter(request, response) }
    }

    private fun parseJwtToken(request: HttpServletRequest): String? = request.getHeader("Authorization")
        .takeIf { it != null && it.startsWith("Bearer ") && it.split(" ").size == 2 }?.substring(7)

    private fun buildLoggedUser(token: String): UsernamePasswordAuthenticationToken? {
        val parsedToken: Jws<Claims> = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)

        val email: String = parsedToken.body.subject ?: return null
        val authorities = mapRoles(parsedToken) + mapPrivileges(parsedToken)

        return UsernamePasswordAuthenticationToken(User(email, "password", authorities), email, authorities)
    }

    private fun mapRoles(parsedToken: Jws<Claims>): List<SimpleGrantedAuthority> =
        parsedToken.body["roles"]?.let {
            (it as List<*>).map { role -> SimpleGrantedAuthority("ROLE_$role") }
        } ?: emptyList()


    private fun mapPrivileges(parsedToken: Jws<Claims>): List<SimpleGrantedAuthority> =
        parsedToken.body["privileges"]?.let {
            (it as List<*>).map { privilege -> SimpleGrantedAuthority(privilege.toString()) }
        } ?: emptyList()
}
