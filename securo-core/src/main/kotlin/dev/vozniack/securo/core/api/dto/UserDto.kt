package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class UserDto(

    val id: UUID,

    val scope: ScopeType,

    val email: String,

    val firstName: String,
    val lastName: String,

    val language: String,

    val active: Boolean,
)

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
