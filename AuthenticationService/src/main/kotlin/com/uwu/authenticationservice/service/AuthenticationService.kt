package com.uwu.authenticationservice.service

import com.uwu.authenticationservice.dto.MemberData
import com.uwu.authenticationservice.entity.Role
import com.uwu.authenticationservice.entity.UserEntity
import com.uwu.authenticationservice.repository.UserRepository
import com.uwu.authenticationservice.request.AuthenticationRequest
import com.uwu.authenticationservice.request.RegistrationRequest
import com.uwu.authenticationservice.response.AuthenticationResponse
import jakarta.mail.internet.AddressException
import jakarta.mail.internet.InternetAddress
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val mailService: MailService,
    private val passwordEncoder: PasswordEncoder
) {

    fun authorization(request: AuthenticationRequest): Any {
        try {
            if (request.email.isNotEmpty() && request.password.isNotEmpty() && isValidEmailAddress(request.email)
            ) {
                authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
                val user = userRepository.findByEmail(request.email).orElseThrow()
                return ResponseEntity(
                    AuthenticationResponse(jwtService.generateToken(user), MemberData().apply {
                        this.email = user.email
                        this.isActivated = user.isActivated
                        this.role = user.role
                    }),
                    HttpStatus.OK
                )
            } else {
                return ResponseEntity(mapOf("message" to "Поля логин и/или пароль какого-то хуя пустые"), HttpStatus.BAD_REQUEST)
            }
        } catch (ex: Exception) {
            return ResponseEntity(
                mapOf("message" to "Неверные логин и/или пароль"),
                HttpStatus.BAD_REQUEST
            )
        }
    }

    fun registration(request: RegistrationRequest): Any {
        try {
            if (request.email.isNotEmpty() && request.password.isNotEmpty() && request.name.isNotEmpty() &&
                request.lastname.isNotEmpty() && isValidEmailAddress(request.email)) {

                val emails = userRepository.findAllEmails()

                emails.forEach { email ->
                    if (request.email == email) return ResponseEntity(
                        mapOf("message" to "Пользователь с таким email уже существует"),
                        HttpStatus.BAD_REQUEST
                    )
                }

                val user = UserEntity().apply {
                    this.email = request.email
                    this.authPassword = passwordEncoder.encode(request.password)
                    this.name = request.name
                    this.lastname = request.lastname
                    this.isActivated = false
                    this.role = Role.USER
                }

                userRepository.save(user)
                return ResponseEntity(
                    AuthenticationResponse(jwtService.generateToken(user), MemberData().apply {
                        this.email = user.email
                        this.isActivated = user.isActivated
                        this.role = user.role
                    }),
                    HttpStatus.OK
                )

            } else {
                return ResponseEntity(
                    mapOf("message" to "Заполнены не все данные!!!"),
                    HttpStatus.BAD_REQUEST
                )
            }
        } catch (ex: Exception) {
            return ResponseEntity(
                mapOf("message" to "Что то пошло не так хз"),
                HttpStatus.BAD_REQUEST
            )
        }
    }

    fun refresh(token: String): Any {
        return try {
            if (token.isNotEmpty()) {
                val user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7))).orElseThrow()
                AuthenticationResponse(jwtService.generateToken(user), MemberData().apply {
                    this.email = user.email
                    this.isActivated = user.isActivated
                    this.role = user.role
                })
            } else {
                ResponseEntity.badRequest()
            }
        } catch (ex: Exception) {
            ResponseEntity.badRequest()
        }
    }

//    fun verify(uuid: String): Any {
//        return try {
//            val user = userRepository.findUserEntityById(uuid)
//            user.isActivated = true
//            userRepository.save(user)
//            ResponseEntity(mapOf("verify" to "success"), HttpStatus.OK)
//        } catch (ex: Exception) {
//            ResponseEntity(mapOf("verify" to "failed"), HttpStatus.BAD_REQUEST)
//        }
//    }
//
//    fun repeatVerify(): Any {
//        return 10
//    }

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