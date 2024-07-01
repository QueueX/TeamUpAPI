package com.uwu.authenticationservice.request

data class RegistrationRequest(
    var email: String,
    var password: String,
    var name: String,
    var lastname: String
)
