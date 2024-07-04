package com.uwu.authenticationservice.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "\"Users\"")
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    lateinit var name: String
    lateinit var lastname: String

    @Column(unique = true)
    lateinit var email: String
    var isActivated: Boolean? = null

    lateinit var password: String

    @Enumerated(EnumType.STRING)
    lateinit var role: Role

    @Column(unique = true, nullable = true)
    var refreshToken: String? = null

}