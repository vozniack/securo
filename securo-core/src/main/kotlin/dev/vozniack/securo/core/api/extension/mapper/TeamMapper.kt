package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.TeamDto
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreateTeamRequestDto.toTeam(): Team = Team(
    name = name,
    code = code,
    description = description,
)

fun Team.toDto(): TeamDto = TeamDto(
    id = id,
    scope = scope,
    name = name,
    code = code,
    description = description,
    createdAt = createdAt.toStringLocalDateTime(),
    updatedAt = updatedAt?.toStringLocalDateTime()
)
