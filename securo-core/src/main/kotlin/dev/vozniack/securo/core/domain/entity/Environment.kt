package dev.vozniack.securo.core.domain.entity

import dev.vozniack.securo.core.domain.entity.common.Auditable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "environments")
data class Environment(

    @Id @GeneratedValue
    @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Column(nullable = false) var name: String,
    @Column(nullable = false) var code: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) var type: EnvironmentType,

    @Column(nullable = true) var description: String? = null,

    @Column(nullable = false) var index: Int

) : Auditable()

enum class EnvironmentType {
    DEV, TEST, STAGE, PROD
}
