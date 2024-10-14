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
)

data class CreateRoleRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,

    val systemId: UUID
)

data class UpdateRoleRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,
)
