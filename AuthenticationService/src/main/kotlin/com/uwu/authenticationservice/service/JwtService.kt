package com.uwu.authenticationservice.service

import com.uwu.authenticationservice.entity.UserEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService {

    @Value("\${token.secret.key}")
    private val jwtSigningKey = ""

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T =
        claimsResolver.apply(extractAllClaims(token))

    fun getSingInKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey))

    fun generateToken(userDetails: UserEntity) = generateToken(HashMap(), userDetails)

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String =
        Jwts.builder().addClaims(extraClaims).setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSingInKey(), SignatureAlgorithm.HS256)
            .compact()

    private fun extractAllClaims(token: String): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(getSingInKey())
            .build()
            .parseClaimsJws(token)!!.body

}