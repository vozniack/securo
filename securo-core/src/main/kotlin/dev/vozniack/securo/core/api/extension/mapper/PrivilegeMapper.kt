package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.PrivilegeDto
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreatePrivilegeRequestDto.toPrivilege(system: System, parent: Privilege? = null): Privilege = Privilege(
    name = name,
    code = code,
    description = description,
    index = index,
    system = system,
    parent = parent
)

fun Privilege.toDto(): PrivilegeDto = PrivilegeDto(
    id = id,
    scope = scope,
    name = name,
    code = code,
    description = description,
    index = index,
    systemId = system.id,
    parentId = parent?.id,
    createdAt = createdAt.toStringLocalDateTime(),
    updatedAt = updatedAt?.toStringLocalDateTime()
)
