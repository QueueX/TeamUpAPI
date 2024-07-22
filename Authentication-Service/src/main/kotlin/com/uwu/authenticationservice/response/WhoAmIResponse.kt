package com.uwu.authenticationservice.response

import com.uwu.migrationservice.entity.Role
import com.uwu.migrationservice.entity.UserEntity
import java.util.UUID

data class WhoAmIResponse(
    var id: UUID?,
    var email: String?,
    var name: String?,
    var lastname: String?,
    var isActivated: Boolean?,
    var role: Role?
) {
    companion object {
        fun of(record: UserEntity) =
            WhoAmIResponse(
                record.id,
                record.email,
                record.name,
                record.lastname,
                record.isActivated,
                record.role)
    }
}
