package com.uwu.authenticationservice.response

import com.uwu.authenticationservice.entity.Role
import java.util.UUID

data class WhoAmIResponse(
    var id: UUID?,
    var email: String?,
    var name: String?,
    var lastname: String?,
    var isActivated: Boolean?,
    var role: Role?
)
