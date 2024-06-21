package com.uwu.authenticationservice.repository

import com.uwu.authenticationservice.entity.MailVerifyEntity
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@Hidden
interface MailVerifyRepository : JpaRepository<MailVerifyEntity, String>{
    fun getMailVerificationEntityByEmail(email: String): MailVerifyEntity
}