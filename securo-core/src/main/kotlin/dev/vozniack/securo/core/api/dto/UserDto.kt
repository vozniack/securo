package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class UserDto(

    val id: UUID,

    val scope: ScopeType,

    val email: String,

    val phonePrefix: String? = null,
    val phoneNumber: String? = null,

    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val language: String,

    val country: String? = null,
    val city: String? = null,
    val zip: String? = null,
    val street: String? = null,
    val house: String? = null,

    val active: Boolean,

    val createdAt: String,
    val updatedAt: String? = null
)

data class CreateUserRequestDto(
    val email: String,
    val password: String,

    val firstName: String,
    val lastName: String,

    val dateOfBirth: String,

    val language: String
)

data class UpdateUserRequestDto(
    val phonePrefix: String? = null,
    val phoneNumber: String? = null,

    val firstName: String,
    val lastName: String,

    val dateOfBirth: String,

    val country: String? = null,
    val city: String? = null,
    val zip: String? = null,
    val street: String? = null,
    val house: String? = null,
)

data class UpdateUserPasswordRequestDto(
    val password: String
)

data class UpdateUserLanguageRequestDto(
    val language: String
)
