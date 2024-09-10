package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.UpdateSystemRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID

fun mockSystem(
    scope: ScopeType = ScopeType.EXTERNAL,
    name: String = "System",
    code: String = "SYS",
    description: String = "System description",
    parent: System? = null
): System = System(
    scope = scope,
    name = name,
    code = code,
    description = description,
    parent = parent
)

fun mockCreateSystemRequestDto(
    name: String = "System",
    code: String = "SYS",
    description: String = "System description",
    parentId: UUID? = null
): CreateSystemRequestDto = CreateSystemRequestDto(
    name = name,
    code = code,
    description = description,
    parentId = parentId
)

fun mockUpdateSystemRequestDto(
    name: String = "System Zero",
    code: String = "SYS0",
    description: String = "System zero description",
    parentId: UUID? = null
): UpdateSystemRequestDto = UpdateSystemRequestDto(
    name = name,
    code = code,
    description = description,
    parentId = parentId
)
