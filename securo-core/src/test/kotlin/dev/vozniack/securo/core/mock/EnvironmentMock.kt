package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentRequestDto
import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.domain.entity.EnvironmentType

fun mockEnvironment(
    name: String = "Production",
    code: String = "PROD",
    type: EnvironmentType = EnvironmentType.PROD,
    description: String = "Production environment",
    index: Int = 1
): Environment = Environment(
    name = name,
    code = code,
    type = type,
    description = description,
    index = index,
)

fun mockCreateEnvironmentRequestDto(
    name: String = "Production",
    code: String = "PROD",
    type: EnvironmentType = EnvironmentType.PROD,
    description: String = "Production environment",
): CreateEnvironmentRequestDto = CreateEnvironmentRequestDto(
    name = name,
    code = code,
    type = type,
    description = description,
)

fun mockUpdateEnvironmentRequestDto(
    name: String = "Pre-Production",
    code: String = "PRE-PROD",
    type: EnvironmentType = EnvironmentType.STAGE,
    description: String = "Pre-production environment",
): UpdateEnvironmentRequestDto = UpdateEnvironmentRequestDto(
    name = name,
    code = code,
    type = type,
    description = description,
)
