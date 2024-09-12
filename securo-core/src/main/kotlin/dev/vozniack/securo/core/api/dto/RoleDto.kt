package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class RoleDto(

    val id: UUID,

    val scope: ScopeType,

    var name: String,
    var code: String,

    var description: String? = null,

    var active: Boolean,
)

data class CreateRoleRequestDto(
    var name: String,
    var code: String,

    var description: String? = null,

    var systemId: UUID
)

data class UpdateRoleRequestDto(
    var name: String,
    var code: String,

    var description: String? = null,

    var systemId: UUID
)
