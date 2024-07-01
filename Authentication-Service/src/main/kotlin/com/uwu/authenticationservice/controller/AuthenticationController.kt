package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.request.AuthenticationRequest
import com.uwu.authenticationservice.request.RegistrationRequest
import com.uwu.authenticationservice.response.AuthenticationResponse
import com.uwu.authenticationservice.service.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authentication")
@Tag(
    name = "Authentication Controller",
    description = "Основной контроллер аутентификации"
)
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    @PostMapping("/authorization")
    @Operation(summary = "Авторизация пользователя")
    fun authorization(@RequestBody request: AuthenticationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Request to authorization")
        return ResponseEntity.ok(authenticationService.authorization(request, response))
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя")
    fun registration(@RequestBody request: RegistrationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Request to registration")
        return ResponseEntity.ok(authenticationService.registration(request, response))
    }

    @GetMapping("/refresh")
    @Operation(
        summary = "Обновление токена",
        description = "ЭТО БУДЕТ ПОТОМ ОТДЕЛЬНЫМ МАЛЮСЕНЬКИМ СЕРВИСОМ ИБО БОЛЬШАЯ НАГРУЗКА ТОГДА ЛЯЖЕТ. В общем эта хрень " +
                "используется чтобы обновлять токен. Токен живет 10 минут, потому я ОООООЧЕНЬ рекомендую кидать туда " +
                "запрос после КАЖДОГО действия на сайте. Благо все что нужно - наличие заголовка с токеном"
    )
    fun refresh(@CookieValue(value = "refreshToken") token: String, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Request to refresh token")
        return ResponseEntity.ok(authenticationService.refresh(token, response))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}