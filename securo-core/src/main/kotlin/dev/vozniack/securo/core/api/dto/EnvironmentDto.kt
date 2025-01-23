package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.entity.EnvironmentType
import java.util.UUID

data class EnvironmentDto(
    val id: UUID,

    val name: String,
    val code: String,

    val type: EnvironmentType,

    val description: String? = null,

    val index: Int,

    val createdAt: String,
    val updatedAt: String? = null
)

data class CreateEnvironmentRequestDto(
    val name: String,
    val code: String,

    val type: EnvironmentType,

    val description: String? = null,
)

data class UpdateEnvironmentRequestDto(
    val name: String,
    val code: String,

    val type: EnvironmentType,

    val description: String? = null,
)
