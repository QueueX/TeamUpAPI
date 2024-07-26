package com.uwu.authenticationservice.service

import com.uwu.authenticationservice.dto.MemberData
import com.uwu.authenticationservice.dto.User
import com.uwu.migrationservice.entity.MailVerifyEntity
import com.uwu.authenticationservice.repository.MailVerifyRepository
import com.uwu.authenticationservice.repository.UserRepository
import com.uwu.authenticationservice.request.MailVerifyRequest
import com.uwu.authenticationservice.response.MailVerifyResponse
import com.uwu.authenticationservice.response.SimpleResponse
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MailService(
    private val mailSender: JavaMailSender,
    private val mailVerifyRepository: MailVerifyRepository,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val authenticationService: AuthenticationService
) {
    private val logger: Logger = LoggerFactory.getLogger(MailService::class.java)

    fun sendEmailVerificationCode(token: String): SimpleResponse {
        logger.info("Generation verification code")
        val email = jwtService.extractUsername(token.substring(7))
        val verificationCode = generateVerificationCode(60)

        val mailVerification = MailVerifyEntity().apply {
            this.email = email
            this.verificationCode = verificationCode
        }
        logger.info("Verification code for user $email has been generated!")
        logger.debug("Verification code for user $email: $verificationCode")
        mailVerifyRepository.save(mailVerification)

        sendMailVerificationMessage(email)
        return SimpleResponse("Код был отправлен на почту $email")
    }

    @Transactional
    fun verifyEmailByCode(token: String, mailVerifyRequest: MailVerifyRequest, response: HttpServletResponse): MailVerifyResponse {
        val email = jwtService.extractUsername(token.substring(7))
        val mailVerify = mailVerifyRepository.getMailVerificationEntityByEmail(email)

        if (mailVerify.verificationCode != mailVerifyRequest.verify) {
            logger.info("Verify code is invalid")
            throw Exception("Неверный код подтверждения email")
        }

        val user = userRepository.findByEmail(email)
        user.isActivated = true
        userRepository.save(user)
        logger.debug("User $email has been activated")

        mailVerifyRepository.delete(mailVerify)
        logger.info("Verification successful")

        val userDetails = User.of(user)

        val tokens = jwtService.generateTokens(userDetails)
        user.refreshToken = tokens[1]
        userRepository.save(user)
        authenticationService.setRefreshToken(response, user)

        return MailVerifyResponse(
            "Верификация ${user.email} прошла успешно", tokens[0], MemberData.of(user)
        )
    }

    private fun sendMailVerificationMessage(to: String) {
        logger.info("Sending mail to verify is began")

        val mailVerification = mailVerifyRepository.getMailVerificationEntityByEmail(to)
        val verificationCode = mailVerification.verificationCode

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setFrom("no-reply@gmail.com")
        helper.setTo(to)
        helper.setSubject("Подтверждение e-mail")
        helper.setText(MailGenerator.mailVerification(verificationCode), true) // true for HTML

        mailSender.send(message)
        logger.debug("Mail to $to has been sent")
        logger.info("Sending mail is successful")
    }

    fun generateVerificationCode(length: Int): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}