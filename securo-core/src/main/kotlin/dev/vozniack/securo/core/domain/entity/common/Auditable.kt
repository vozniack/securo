package dev.vozniack.securo.core.domain.entity.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class Auditable(

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var updatedAt: LocalDateTime? = null
)
