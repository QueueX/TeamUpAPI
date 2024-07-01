package com.uwu.authenticationservice.configuration

import com.uwu.authenticationservice.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.core.userdetails.UserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader("Authorization")
            logger.debug("Authorization header: $authHeader")

            if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }

            val jwt = authHeader.substring(7)
            logger.debug("JWT token: $jwt")

            val userEmail = jwtService.extractUsername(jwt)
            logger.debug("User email: $userEmail")

            if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = this.userDetailsService.loadUserByUsername(userEmail)
                logger.debug("User details: $userDetails")

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(userEmail, null, userDetails.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                    logger.debug("Authentication success")
                } else {
                    logger.debug("Invalid token")
                }
            }

            filterChain.doFilter(request, response)
        } catch (ex: ExpiredJwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Срок действия токена истек")
            return
        }
    }

}