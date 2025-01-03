package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class SystemDto(

    val id: UUID,

    val scope: ScopeType,

    val name: String,
    val code: String,

    val description: String? = null,

    val active: Boolean,
)

data class CreateSystemRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,

    val parentId: UUID? = null
)

data class UpdateSystemRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,

    val parentId: UUID? = null
)
