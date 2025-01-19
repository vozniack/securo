package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class RoleDto(

    val id: UUID,

    val scope: ScopeType,

    val name: String,
    val code: String,

    val description: String? = null,

    val active: Boolean,

    val createdAt: String,
    val updatedAt: String? = null
)

data class CreateRoleRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,

    val teamId: UUID
)

data class UpdateRoleRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,
)
