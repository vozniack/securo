package dev.vozniack.securo.core.domain.entity.extension

import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.entity.SystemDto
import dev.vozniack.securo.core.domain.entity.System

fun System.toDto(): SystemDto = SystemDto(
    id = id,
    scope = scope,
    name = name,
    code = code,
    description = description,
    active = active
)

fun CreateSystemRequestDto.toSystem(parent: System? = null): System =
    System(
        name = name,
        code = code,
        description = description,
        parent = parent
    )
