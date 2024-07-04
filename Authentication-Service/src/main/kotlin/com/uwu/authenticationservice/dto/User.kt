package com.uwu.authenticationservice.dto

import com.uwu.authenticationservice.entity.Role
import com.uwu.authenticationservice.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User (
    val email: String,
    val authPassword: String,
    val role: Role
) : UserDetails {

    companion object {
        fun of (record: UserEntity) = User(record.email, record.password, record.role)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.name))

    override fun getPassword() = this.authPassword
    override fun getUsername() = this.email

}