package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.Team
import java.util.UUID

fun mockRole(
    scope: ScopeType = ScopeType.EXTERNAL,
    name: String = "Admin",
    code: String = "ADMIN",
    description: String = "Admin role description",
    team: Team
): Role = Role(
    scope = scope,
    name = name,
    code = code,
    description = description,
    team = team
)

fun mockCreateRoleRequestDto(
    name: String = "Admin",
    code: String = "ADMIN",
    description: String = "Admin role description",
    teamId: UUID
): CreateRoleRequestDto = CreateRoleRequestDto(
    name = name,
    code = code,
    description = description,
    teamId = teamId
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
