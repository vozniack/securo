package dev.vozniack.securo.core.api.dto.entity

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
