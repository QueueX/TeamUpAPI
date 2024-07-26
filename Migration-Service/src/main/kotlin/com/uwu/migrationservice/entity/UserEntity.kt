package com.uwu.migrationservice.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    var id: UUID? = null

    @Column(nullable = false, unique = false)
    lateinit var name: String

    @Column(nullable = false, unique = false)
    lateinit var lastname: String

    @Column(unique = true, nullable = false)
    lateinit var email: String

    @Column(nullable = false, unique = false)
    var isActivated: Boolean? = null

    @Column(nullable = false, unique = false)
    lateinit var password: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    lateinit var role: Role

}