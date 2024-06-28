package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.response.WhoAmIResponse
import com.uwu.authenticationservice.service.WhoAmIService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authentication/whoami")
@Tag(
    name = "WhoAmI",
    description = "Полная инфа об аутентифицированном пользователе"
)
class WhoAmIController(private val whoAmIService: WhoAmIService) {
    private val logger: Logger = LoggerFactory.getLogger(WhoAmIController::class.java)

    @GetMapping
    @Operation(description = "Полная инфа об аутентифицированном пользователе. Поверь, позже пригодится!!!")
    fun whoAmI(@RequestHeader(value = "Authorization") token: String): ResponseEntity<WhoAmIResponse> {
        logger.info("Request to WhoAmI")
        return ResponseEntity.ok(whoAmIService.whoAmI(token))
    }

}