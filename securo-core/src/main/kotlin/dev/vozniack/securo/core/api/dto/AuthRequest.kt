package dev.vozniack.securo.core.api.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)
