package com.uwu.authenticationservice.service

import com.uwu.authenticationservice.dto.MemberData
import com.uwu.authenticationservice.dto.User
import com.uwu.authenticationservice.entity.Role
import com.uwu.authenticationservice.entity.UserEntity
import com.uwu.authenticationservice.repository.UserRepository
import com.uwu.authenticationservice.request.AuthenticationRequest
import com.uwu.authenticationservice.request.RegistrationRequest
import com.uwu.authenticationservice.response.AuthenticationResponse
import com.uwu.authenticationservice.response.SimpleResponse
import jakarta.mail.internet.AddressException
import jakarta.mail.internet.InternetAddress
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationService::class.java)

    @Transactional
    fun authorization(request: AuthenticationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidAuthenticationCredentials(request)) {
            logger.error("Поля логин и/или пароль какого-то хуя пустые")
            throw Exception("Поля логин и/или пароль какого-то хуя пустые")
        }
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val user = userRepository.findByEmail(request.email)
        logger.debug("User ${user.email} is authorized")
        logger.info("Authorization is successful!")

        val userDetails = User.of(user)

        val tokens = jwtService.generateTokens(userDetails)

        user.refreshToken = tokens[1]
        userRepository.save(user)
        setRefreshToken(response, user)

        return AuthenticationResponse(tokens[0], MemberData.of(user))
    }

    @Transactional
    fun registration(request: RegistrationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidRegistrationCredentials(request)) {
            logger.error("Data is empty")
            throw Exception("Заполнены не все данные!!!")
        }

        val emails = userRepository.findAllEmails()

        emails.forEach { email ->
            if (request.email == email) {
                logger.error("Error of registration: User with email ${request.email} is already exist")
                throw Exception("Пользователь с таким email уже существует")
            }
        }

        val user = UserEntity().apply {
            this.email = request.email
            this.password = passwordEncoder.encode(request.password)
            this.name = request.name
            this.lastname = request.lastname
            this.isActivated = false
            this.role = Role.USER
            this.refreshToken = null
        }

        val userDetails = User.of(user)

        val tokens = jwtService.generateTokens(userDetails)
        user.refreshToken = tokens[1]
        userRepository.save(user)
        setRefreshToken(response, user)

        logger.debug("User with email ${user.email} has been created")
        logger.info("Registration is successful!")

        return AuthenticationResponse(tokens[0], MemberData.of(user))
    }

    fun logout(token: String, response: HttpServletResponse): SimpleResponse {
        val user = userRepository.findByEmail(jwtService.extractUsername(token))
        user.refreshToken = null

        val cookie = Cookie("refreshToken", null)
        cookie.maxAge = 0
        cookie.path = "/"
        response.addCookie(cookie)

        return SimpleResponse("Logout successful")
    }

    @Transactional
    fun refresh(token: String, response: HttpServletResponse): AuthenticationResponse {
        if (token.isEmpty()) {
            logger.error("Token is empty")
            throw Exception("Token is empty")
        }

        val user = userRepository.findByEmail(jwtService.extractUsername(token))

        if (user.refreshToken != token) {
            logger.error("")
            throw Exception("Token not valid")
        }

        val userDetails = User.of(user)

        val tokens = jwtService.generateTokens(userDetails)
        user.refreshToken = tokens[1]
        userRepository.save(user)
        setRefreshToken(response, user)

        logger.debug("Token of user ${user.email} is refreshed")

        return AuthenticationResponse(tokens[0], MemberData.of(user))
    }

    fun setRefreshToken(response: HttpServletResponse, user: UserEntity) {
        val cookie = Cookie("refreshToken", user.refreshToken)
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.path = "/"
        cookie.maxAge = 30 * 24 * 60 * 60 // 30 дней

        response.addCookie(cookie)
    }

    private fun isValidAuthenticationCredentials(request: AuthenticationRequest) =
        request.email.isNotEmpty() && request.password.isNotEmpty() && isValidEmailAddress(request.email)

    private fun isValidRegistrationCredentials(request: RegistrationRequest) =
        request.email.isNotEmpty() && request.password.isNotEmpty() && request.name.isNotEmpty() &&
                request.lastname.isNotEmpty() && isValidEmailAddress(request.email)

    private fun isValidEmailAddress(email: String?): Boolean {
        try {
            val emailAddr = InternetAddress(email)
            emailAddr.validate()
            return true
        } catch (ex: AddressException) {
            throw AddressException("Email is not valid")
        }
    }

}