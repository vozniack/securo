package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.UpdatePrivilegeRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID

fun mockPrivilege(
    scope: ScopeType = ScopeType.EXTERNAL,
    name: String = "Login",
    code: String = "LOGIN",
    description: String = "Privilege to log in",
    index: Int = 0,
    system: System,
    parent: Privilege? = null
): Privilege = Privilege(
    scope = scope,
    name = name,
    code = code,
    description = description,
    index = index,
    system = system,
    parent = parent
)

fun mockCreatePrivilegeRequestDto(
    name: String = "Login",
    code: String = "LOGIN",
    description: String = "Privilege to log in",
    index: Int = 0,
    systemId: UUID,
    parentId: UUID? = null
): CreatePrivilegeRequestDto = CreatePrivilegeRequestDto(
    name = name,
    code = code,
    description = description,
    index = index,
    systemId = systemId,
    parentId = parentId
)

fun mockUpdatePrivilegeRequestDto(
    name: String = "Signup",
    code: String = "SIGNUP",
    description: String = "Privilege to sign up",
): UpdatePrivilegeRequestDto = UpdatePrivilegeRequestDto(
    name = name,
    code = code,
    description = description
)
