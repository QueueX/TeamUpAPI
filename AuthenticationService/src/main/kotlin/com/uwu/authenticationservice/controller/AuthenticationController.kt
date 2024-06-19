package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.request.AuthenticationRequest
import com.uwu.authenticationservice.request.RegistrationRequest
import com.uwu.authenticationservice.service.AuthenticationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/authorization")
    fun authorization(@RequestBody request: AuthenticationRequest) = authenticationService.authorization(request)

    @PostMapping("/registration")
    fun registration(@RequestBody request: RegistrationRequest) = authenticationService.registration(request)

    @GetMapping("/refresh")
    fun refresh(@RequestHeader(value = "Authorization") token: String) = authenticationService.refresh(token)

}