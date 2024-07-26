package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.service.PasswordService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/password")
@Tag(
    name = "Password Controller",
    description = "Контроллер работы с паролями"
)
class PasswordController(
    private val passwordService: PasswordService
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)



    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}