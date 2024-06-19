package com.uwu.authenticationservice.repository

import com.uwu.authenticationservice.entity.UserEntity
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
@Hidden
interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): Optional<UserEntity>

    @Query("select u.email from UserEntity u")
    fun findAllEmails(): MutableList<String>

}
