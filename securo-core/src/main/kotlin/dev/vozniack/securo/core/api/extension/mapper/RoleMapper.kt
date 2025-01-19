package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.RoleDto
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreateRoleRequestDto.toRole(team: Team): Role = Role(
    name = name,
    code = code,
    description = description,
    team = team
)

fun Role.toDto(): RoleDto = RoleDto(
    id = id,
    scope = scope,
    name = name,
    code = code,
    description = description,
    active = active,
    createdAt = createdAt.toStringLocalDateTime(),
    updatedAt = updatedAt?.toStringLocalDateTime()
)
