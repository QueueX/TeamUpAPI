package com.uwu.authenticationservice.repository

import com.uwu.migrationservice.entity.UserEntity
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
@Hidden
interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity

    @Query("select u.email from UserEntity u")
    fun findAllEmails(): List<String>
}
