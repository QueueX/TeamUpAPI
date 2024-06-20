package com.uwu.authenticationservice.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(private val mailSender: JavaMailSender) {
    private val logger: Logger = LoggerFactory.getLogger(MailService::class.java)

    fun sendMessageVerify(to: String, subject: String, text: String) {
        logger.info("Sending mail to verify is begined")
        try {
            val message = SimpleMailMessage().apply {
                from = "no-reply@gmail.com"
                setTo(to)
                setSubject(subject)
                setText(text)
            }
            mailSender.send(message)
            logger.debug("Mail to $subject has been sent")
            logger.info("Sending mail is successful")
        } catch (ex: Exception) {
            println(ex)
        }
    }
}