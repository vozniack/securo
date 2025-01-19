package dev.vozniack.securo.core.domain.entity

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.common.Auditable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "teams")
data class Team(

    @Id @GeneratedValue
    @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) val scope: ScopeType = ScopeType.EXTERNAL,

    @Column(nullable = false) var name: String,
    @Column(nullable = false) var code: String,

    @Column(nullable = true) var description: String? = null,

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // #todo lazy fetch
    var roles: MutableList<Role> = mutableListOf(),
) : Auditable()
