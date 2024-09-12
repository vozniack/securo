package dev.vozniack.securo.core.domain.entity

import dev.vozniack.securo.core.domain.ScopeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID


@Entity
@Table(name = "roles")
data class Role(

    @Id @GeneratedValue @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) val scope: ScopeType = ScopeType.EXTERNAL,

    @Column(nullable = false) var name: String,
    @Column(nullable = false) var code: String,

    @Column(nullable = true) var description: String? = null,

    @Column(nullable = false) var active: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "system_id", nullable = false)
    val system: System
)
