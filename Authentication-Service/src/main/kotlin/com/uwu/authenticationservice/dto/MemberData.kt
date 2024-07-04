package com.uwu.authenticationservice.dto

import com.uwu.authenticationservice.entity.Role
import com.uwu.authenticationservice.entity.UserEntity

data class MemberData(
    val email: String,
    val isActivated: Boolean,
    val role: Role
) {
    companion object {
        fun of(record: UserEntity) = MemberData(record.email, record.isActivated!!, record.role)
    }
}
