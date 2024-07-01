package com.uwu.authenticationservice.dto

import com.uwu.authenticationservice.entity.Role

data class MemberData(
    var email: String? = null,
    var isActivated: Boolean? = null,
    var role: Role? = null
)
