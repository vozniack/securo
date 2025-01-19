package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.SystemDto
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreateSystemRequestDto.toSystem(parent: System? = null): System = System(
    name = name,
    code = code,
    description = description,
    icon = icon,
    parent = parent
)

fun System.toDto(): SystemDto = SystemDto(
    id = id,
    scope = scope,
    name = name,
    code = code,
    description = description,
    icon = icon,
    active = active,
    createdAt = createdAt.toStringLocalDateTime(),
    updatedAt = updatedAt?.toStringLocalDateTime()
)
