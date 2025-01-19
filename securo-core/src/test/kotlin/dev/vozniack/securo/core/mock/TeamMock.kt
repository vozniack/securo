package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.UpdateTeamRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Team

fun mockTeam(
    scope: ScopeType = ScopeType.EXTERNAL,
    name: String = "Securo Team",
    code: String = "SEC",
    description: String = "Internal Securo Team",
): Team = Team(
    scope = scope,
    name = name,
    code = code,
    description = description
)

fun mockCreateTeamRequestDto(
    name: String = "Securo Team",
    code: String = "SEC",
    description: String = "Internal Securo Team",
): CreateTeamRequestDto = CreateTeamRequestDto(
    name = name,
    code = code,
    description = description
)

fun mockUpdateTeamRequestDto(
    name: String = "Securo External Team",
    code: String = "SEC_EXT",
    description: String = "External Securo Team",
): UpdateTeamRequestDto = UpdateTeamRequestDto(
    name = name,
    code = code,
    description = description
)
