package dev.vozniack.securo.core.domain.entity

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.common.Auditable
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
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue
    @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) val scope: ScopeType = ScopeType.EXTERNAL,

    @Column(nullable = false) val email: String,
    @Column(nullable = true) var password: String?,

    @Column(nullable = true) var phonePrefix: String? = null,
    @Column(nullable = true) var phoneNumber: String? = null,

    @Column(nullable = false) var firstName: String,
    @Column(nullable = false) var lastName: String,
    @Column(nullable = false) var dateOfBirth: LocalDate,
    @Column(nullable = false) var language: String,

    @Column(nullable = true) var country: String? = null,
    @Column(nullable = true) var city: String? = null,
    @Column(nullable = true) var zip: String? = null,
    @Column(nullable = true) var street: String? = null,
    @Column(nullable = true) var house: String? = null,

    @Column(nullable = false) var active: Boolean = true,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER) // #todo lazy fetch
    @JoinTable(
        name = "user_systems",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "system_id")]
    )
    var systems: MutableList<System> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER) // #todo lazy fetch
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableList<Role> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER) // #todo lazy fetch
    var privileges: MutableList<UserPrivilege> = mutableListOf(),
) : Auditable()
