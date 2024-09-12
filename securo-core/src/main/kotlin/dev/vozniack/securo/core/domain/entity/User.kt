package dev.vozniack.securo.core.domain.entity

import dev.vozniack.securo.core.domain.ScopeType
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
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) val scope: ScopeType = ScopeType.EXTERNAL,

    @Column(nullable = false) val email: String,
    @Column(nullable = true) var password: String,

    @Column(nullable = false) var firstName: String,
    @Column(nullable = false) var lastName: String,

    @Column(nullable = false) var language: String = "en_EN",

    @Column(nullable = false) var active: Boolean = true,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_systems",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "system_id")]
    )
    var systems: MutableList<System> = mutableListOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_Roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableList<Role> = mutableListOf()
)
