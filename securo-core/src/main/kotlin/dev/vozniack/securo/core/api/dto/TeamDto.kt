package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class TeamDto(

    val id: UUID,

    val scope: ScopeType,

    val name: String,
    val code: String,

    val description: String? = null,

    val createdAt: String,
    val updatedAt: String? = null
)

data class CreateTeamRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,
)

data class UpdateTeamRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,
)
