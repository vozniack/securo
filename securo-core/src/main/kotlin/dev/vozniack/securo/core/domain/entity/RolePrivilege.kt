package dev.vozniack.securo.core.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "role_privileges")
data class RolePrivilege(

    @Id @GeneratedValue
    @Column(nullable = false) var id: UUID,

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false) var role: Role,

    @ManyToOne
    @JoinColumn(name = "privilege_id", nullable = false) override var privilege: Privilege,

    @Column(name = "excluded", nullable = false) override var excluded: Boolean
) : RelatedPrivilege
