package com.uwu.authenticationservice.entity

import jakarta.persistence.*

@Entity(name = "Verification_Codes")
class MailVerifyEntity {

    @Id
    @Column(unique = true)
    lateinit var email: String

    @Column(name = "verification_code", length = 6)
    lateinit var verificationCode: String

}