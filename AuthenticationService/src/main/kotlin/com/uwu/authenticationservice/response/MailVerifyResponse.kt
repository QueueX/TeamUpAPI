package com.uwu.authenticationservice.response

import com.uwu.authenticationservice.dto.MemberData

data class MailVerifyResponse(
    var message: String,
    var token: String,
    var user: MemberData
)
