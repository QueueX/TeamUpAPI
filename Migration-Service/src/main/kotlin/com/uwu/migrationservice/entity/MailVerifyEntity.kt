package com.uwu.migrationservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "verification_codes")
class MailVerifyEntity {

    @Id
    @Column(unique = true, nullable = false)
    lateinit var email: String

    @Column(length = 60, unique = false, nullable = false)
    lateinit var verificationCode: String

}