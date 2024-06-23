package com.uwu.authenticationservice.service

import com.uwu.authenticationservice.entity.UserEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import java.security.Key
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

@Service
class JwtService {

    @Value("\${token.secret.key}")
    private val jwtSigningKey = ""
    private val logger: Logger = LoggerFactory.getLogger(JwtService::class.java)

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T =
        claimsResolver.apply(extractAllClaims(token))

    fun getSingInKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey))

    fun generateToken(userDetails: UserEntity): String {
        logger.info("Beginning of generate token")
        val header = HashMap<String, Any>()
        header["typ"] = "JWT"
        header["alg"] = "HS256"

        val claims = HashMap<String, Any>()
        claims["isActivated"] = userDetails.isActivated!!
        claims["role"] = userDetails.role.toString()

        logger.debug("Token for ${userDetails.email} has been generated")
        return generateToken(header, claims, userDetails)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        if (isTokenExpired(token)) throw Exception("Срок действия токена истек")
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    fun generateToken(header: Map<String, Any>, claims: Map<String, Any>, userDetails: UserDetails): String =
        Jwts.builder()
            .setHeader(header)
            .setSubject(userDetails.username)
            .addClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 10))
            .signWith(getSingInKey(), SignatureAlgorithm.HS256)
            .compact()

    private fun extractAllClaims(token: String): Claims =
         Jwts
            .parserBuilder()
            .setSigningKey(getSingInKey())
            .build()
            .parseClaimsJws(token)!!.body

}