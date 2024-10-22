package dev.vozniack.securo.core.api.dto

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class PrivilegeDto(

    val id: UUID,

    val scope: ScopeType,

    val name: String,
    val code: String,

    val description: String? = null,

    val index: Int,

    val systemId: UUID,

    val parentId: UUID? = null
)

data class CreatePrivilegeRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,

    val index: Int,

    val systemId: UUID,

    val parentId: UUID? = null
)

data class UpdatePrivilegeRequestDto(
    val name: String,
    val code: String,

    val description: String? = null,

    val parentId: UUID? = null
)
