package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.request.MailVerifyRequest
import com.uwu.authenticationservice.service.MailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authentication/email")
@Tag(
    name = "Mail Verify Controller",
    description = "Контроллер подтверждения email"
)
class MailVerifyController(
    private val mailService: MailService
) {
    private val logger: Logger = LoggerFactory.getLogger(MailVerifyController::class.java)

    @PostMapping("/send-code")
    @Operation(summary = "Генерация и отправка кода верификации на почту клиента")
    fun sendVerificationCode(@RequestHeader(value = "Authorization") token: String): ResponseEntity<Map<String, String>> {
        logger.info("Request to sending verification code")
        mailService.sendVerificationCode(token)
        return ResponseEntity.ok(mapOf("message" to "Verification code has been sent"))
    }

    @PostMapping("/verify")
    @Operation(summary = "Верификация пользователя по коду")
    fun verifyByCode(
        @RequestHeader(value = "Authorization") token: String,
        @RequestBody request: MailVerifyRequest
    ): ResponseEntity<Map<String, String>> {
        logger.info("Request to verify")
        mailService.verifyByCode(token, request)
        return ResponseEntity.ok(mapOf("message" to "User has been activated"))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }
}