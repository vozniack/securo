package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.EnvironmentDto
import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreateEnvironmentRequestDto.toEnvironment(index: Int): Environment = Environment(
    name = name,
    code = code,
    type = type,
    description = description,
    index = index
)

fun Environment.toDto(): EnvironmentDto = EnvironmentDto(
    id = id,
    name = name,
    code = code,
    type = type,
    description = description,
    index = index,
    createdAt = createdAt.toStringLocalDateTime(),
    updatedAt = updatedAt?.toStringLocalDateTime()
)
