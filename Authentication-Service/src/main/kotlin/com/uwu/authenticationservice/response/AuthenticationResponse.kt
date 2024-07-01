package com.uwu.authenticationservice.response

import com.uwu.authenticationservice.dto.MemberData

data class AuthenticationResponse(
    var token: String,
    var user: MemberData
)
