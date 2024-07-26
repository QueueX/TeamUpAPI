package com.uwu.authenticationservice

import com.uwu.authenticationservice.service.MailGenerator
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper

fun main() {
    mailsTest()
}

private fun mailsTest() {
    val to = "truthpruthbg@gmail.com"

    val verificationCode1 = "hgq9389hFHhh89932fuu90u9032ffe242r3sdw"
    val verificationCode2 = "123456"

    val mailSender = JavaMailSenderImpl()
    mailSender.host = "smtp.gmail.com"
    mailSender.port = 587
    mailSender.username = "my.teamup.platform@gmail.com"
    mailSender.password = "pkfqcauuhwmitfzg"

    val props = mailSender.javaMailProperties
    props["mail.transport.protocol"] = "smtp"
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.starttls.enable"] = "true"
    props["mail.debug"] = "true"

    // Первое сообщение
    val message1 = mailSender.createMimeMessage()
    val helper1 = MimeMessageHelper(message1, true, "UTF-8")

    helper1.setFrom("no-reply@gmail.com")
    helper1.setTo(to)
    helper1.setSubject("Подтверждение e-mail")
    helper1.setText(MailGenerator.mailVerification(verificationCode1), true) // true for HTML

    mailSender.send(message1)

    // Второе сообщение
    val message2 = mailSender.createMimeMessage()
    val helper2 = MimeMessageHelper(message2, true, "UTF-8")

    helper2.setFrom("no-reply@gmail.com")
    helper2.setTo(to)
    helper2.setSubject("Код для восстановления пароля")
    helper2.setText(MailGenerator.codeForPasswordRepair(verificationCode2), true)

    mailSender.send(message2)
}
