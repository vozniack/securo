package dev.vozniack.securo.core.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue @Column(nullable = false) val id: UUID = UUID.randomUUID(),

    @Column(nullable = false) val email: String,
    @Column(nullable = true) var password: String,

    @Column(nullable = false) var firstName: String,
    @Column(nullable = false) var lastName: String,

    @Column(nullable = false) var language: String = "en_EN",

    @Column(nullable = false) var active: Boolean = true
)
