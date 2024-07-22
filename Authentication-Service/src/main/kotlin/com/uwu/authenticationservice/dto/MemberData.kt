package com.uwu.authenticationservice.dto

import com.uwu.migrationservice.entity.Role
import com.uwu.migrationservice.entity.UserEntity

data class MemberData(
    val email: String,
    val isActivated: Boolean,
    val role: Role
) {
    companion object {
        fun of(record: UserEntity) = MemberData(record.email, record.isActivated!!, record.role)
    }
}
