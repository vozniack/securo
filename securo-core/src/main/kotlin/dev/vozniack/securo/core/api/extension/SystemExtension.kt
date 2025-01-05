package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.SystemDto
import dev.vozniack.securo.core.api.dto.UpdateSystemRequestDto
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.utils.throwIfTrue

fun CreateSystemRequestDto.validate() {
    throwIfTrue(BadRequestException("Name can't be empty")) { name.isEmpty() }
    throwIfTrue(BadRequestException("Code can't be empty")) { code.isEmpty() }
}

fun UpdateSystemRequestDto.validate() {
    throwIfTrue(BadRequestException("Name can't be empty")) { name.isEmpty() }
    throwIfTrue(BadRequestException("Code can't be empty")) { code.isEmpty() }
}

fun CreateSystemRequestDto.toSystem(parent: System? = null): System =
    System(
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
    active = active
)
