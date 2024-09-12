package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.RoleDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.utils.throwIfTrue

fun CreateRoleRequestDto.validate() {
    throwIfTrue(BadRequestException("Name can't be empty")) { name.isEmpty() }
    throwIfTrue(BadRequestException("Code can't be empty")) { code.isEmpty() }
}

fun UpdateRoleRequestDto.validate() {
    throwIfTrue(BadRequestException("Name can't be empty")) { name.isEmpty() }
    throwIfTrue(BadRequestException("Code can't be empty")) { code.isEmpty() }
}

fun CreateRoleRequestDto.toRole(system: System): Role = Role(
    name = name,
    code = code,
    description = description,
    system = system
)

fun Role.toDto(): RoleDto = RoleDto(
    id = id,
    scope = scope,
    name = name,
    code = code,
    description = description,
    active = active
)
