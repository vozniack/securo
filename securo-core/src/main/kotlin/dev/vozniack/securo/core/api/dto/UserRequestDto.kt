package dev.vozniack.securo.core.api.dto

data class CreateUserRequestDto(
    val email: String,
    val password: String,

    val firstName: String,
    val lastName: String,

    val language: String
)

data class UpdateUserRequestDto(
    val firstName: String,
    val lastName: String,

    val language: String
)

data class UpdateUserPasswordRequestDto(
    val password: String
)
