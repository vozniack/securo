package dev.vozniack.securo.core.api.dto.entity

import dev.vozniack.securo.core.domain.ScopeType
import java.util.UUID

data class SystemDto(

    val id: UUID,

    val scope: ScopeType,

    var name: String,
    var code: String,

    var description: String? = null,

    var active: Boolean,
)
