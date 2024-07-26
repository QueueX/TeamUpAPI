package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.request.MailRecoveryVerifyCode
import com.uwu.authenticationservice.request.MailVerifyRequest
import com.uwu.authenticationservice.response.MailVerifyResponse
import com.uwu.authenticationservice.response.SimpleResponse
import com.uwu.authenticationservice.service.MailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
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

    @PostMapping("/send-verify-code")
    @Operation(summary = "Генерация и отправка кода верификации на почту клиента")
    fun sendEmailVerificationCode(@RequestHeader(value = "Authorization") token: String): ResponseEntity<SimpleResponse> {
        logger.info("Request to sending verification code")
        return ResponseEntity.ok(mailService.sendEmailVerificationCode(token))
    }

//    fun sendRecoveryCode(@RequestBody mailRecoveryVerifyCode: MailRecoveryVerifyCode) : ResponseEntity<SimpleResponse> {
//        logger.info("Request to sending recovery code")
//        ResponseEntity.ok()
//    }

    @PostMapping("/verify")
    @Operation(summary = "Верификация пользователя по коду")
    fun verifyEmailByCode(
        @RequestHeader(value = "Authorization") token: String,
        @RequestBody request: MailVerifyRequest,
        response: HttpServletResponse
    ): ResponseEntity<MailVerifyResponse> {
        logger.info("Request to verify")
        return ResponseEntity.ok(mailService.verifyEmailByCode(token, request, response))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }
}