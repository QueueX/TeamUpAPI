package com.uwu.authenticationservice.entity

import jakarta.persistence.*

@Entity(name = "verification_code")
class MailVerifyEntity {

    @Id
    @Column(unique = true)
    var email: String? = null

    @Column(length = 6)
    var verificationCode: String? = null

}