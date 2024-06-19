package com.uwu.authenticationservice.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Authentication Service",
        description = "Сервис Аутентификации", version = "1.0.0"
    )
)
class SwaggerConfiguration {
}