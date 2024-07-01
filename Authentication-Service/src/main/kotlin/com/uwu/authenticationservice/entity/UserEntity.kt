package com.uwu.authenticationservice.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    var name: String? = null
    var lastname: String? = null

    @Column(unique = true)
    var email: String? = null
    var isActivated: Boolean? = null

    @Column(name = "password")
    var authPassword: String? = null

    @Enumerated(EnumType.STRING)
    var role: Role? = null

    @Column(unique = true, nullable = true)
    var refreshToken: String? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role?.name))

    override fun getPassword() = this.authPassword
    override fun getUsername() = this.email
}