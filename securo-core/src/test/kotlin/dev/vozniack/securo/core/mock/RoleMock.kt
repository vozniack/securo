package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID

fun mockRole(
    scope: ScopeType = ScopeType.EXTERNAL,
    name: String = "Admin",
    code: String = "ADMIN",
    description: String = "Admin role description",
    system: System
): Role = Role(
    scope = scope,
    name = name,
    code = code,
    description = description,
    system = system
)

fun mockCreateRoleRequestDto(
    name: String = "Admin",
    code: String = "ADMIN",
    description: String = "Admin role description",
    systemId: UUID
): CreateRoleRequestDto = CreateRoleRequestDto(
    name = name,
    code = code,
    description = description,
    systemId = systemId
)

fun mockUpdateRoleRequestDto(
    name: String = "User",
    code: String = "USER",
    description: String = "User role description"
): UpdateRoleRequestDto = UpdateRoleRequestDto(
    name = name,
    code = code,
    description = description
)
