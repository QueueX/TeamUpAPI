package com.uwu.authenticationservice.service

import com.uwu.authenticationservice.repository.UserRepository
import com.uwu.authenticationservice.response.WhoAmIResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WhoAmIService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    private val logger: Logger = LoggerFactory.getLogger(WhoAmIService::class.java)

    fun whoAmI(token: String): WhoAmIResponse {
        val user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7)))
        logger.info("WhoAmI for user ${user.email} has been returned")
        return WhoAmIResponse(
            id = user.id,
            email = user.email,
            name = user.name,
            lastname = user.lastname,
            isActivated = user.isActivated,
            role = user.role
        )
    }
}