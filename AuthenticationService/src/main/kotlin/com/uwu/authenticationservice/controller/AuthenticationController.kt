package com.uwu.authenticationservice.controller

import com.uwu.authenticationservice.request.AuthenticationRequest
import com.uwu.authenticationservice.request.RegistrationRequest
import com.uwu.authenticationservice.service.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
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

    @PostMapping("/authorization")
    @Operation(summary = "Авторизация пользователя")
    fun authorization(@RequestBody request: AuthenticationRequest) = authenticationService.authorization(request)

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя"    )
    fun registration(@RequestBody request: RegistrationRequest) = authenticationService.registration(request)

    @GetMapping("/refresh")
    @Operation(
        summary = "Обновление токена",
        description = "ЭТО БУДЕТ ПОТОМ ОТДЕЛЬНЫМ МАЛЮСЕНЬКИМ СЕРВИСОМ ИБО БОЛЬШАЯ НАГРУЗКА ТОГДА ЛЯЖЕТ. В общем эта хрень " +
                "используется чтобы обновлять токен. Токен живет 10 минут, потому я ОООООЧЕНЬ рекомендую кидать туда " +
                "запрос после КАЖДОГО действия на сайте. Благо все что нужно - наличие заголовка с токеном"
    )
    fun refresh(@RequestHeader(value = "Authorization") token: String) = authenticationService.refresh(token)

}