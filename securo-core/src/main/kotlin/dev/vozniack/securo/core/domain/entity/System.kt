package dev.vozniack.securo.core.domain.entity

import dev.vozniack.securo.core.domain.ScopeType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "systems")
data class System(

    @Id @GeneratedValue
    @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) val scope: ScopeType = ScopeType.EXTERNAL,

    @Column(nullable = false) var name: String,
    @Column(nullable = false) var code: String,

    @Column(nullable = true) var description: String? = null,

    @Column(nullable = false) var active: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true) var parent: System? = null,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER) // #todo lazy fetch
    var systems: MutableList<System> = mutableListOf(),

    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER) // #todo lazy fetch
    var roles: MutableList<Role> = mutableListOf(),

    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER) // #todo lazy fetch
    var privileges: MutableList<Privilege> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_systems",
        joinColumns = [JoinColumn(name = "system_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var users: MutableList<User> = mutableListOf()
)
