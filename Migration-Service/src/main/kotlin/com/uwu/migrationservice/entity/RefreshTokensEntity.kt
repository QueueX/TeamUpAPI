package com.uwu.migrationservice.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "refresh_tokens")
class RefreshTokensEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false, unique = false)
    lateinit var user: UserEntity

    @Column(nullable = false, unique = true)
    lateinit var token: String

}