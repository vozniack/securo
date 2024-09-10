package dev.vozniack.securo.core.api.dto

import java.util.UUID

data class CreateSystemRequestDto(
    var name: String,
    var code: String,

    var description: String? = null,

    var parentId: UUID? = null
)

data class UpdateSystemRequestDto(
    var name: String,
    var code: String,

    var description: String? = null,

    var parentId: UUID? = null
)
