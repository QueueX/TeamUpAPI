package com.uwu.authenticationservice.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(private val mailSender: JavaMailSender) {
    fun sendMessageVerify(to: String, subject: String, text: String) {
        try {
            val message = SimpleMailMessage().apply {
                from = "no-reply@gmail.com"
                setTo(to)
                setSubject(subject)
                setText(text)
            }
            mailSender.send(message)
        } catch (ex: Exception) {
            println(ex)
        }
    }
}