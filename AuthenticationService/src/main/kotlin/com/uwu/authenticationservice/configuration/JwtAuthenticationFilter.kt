package com.uwu.authenticationservice.configuration

import com.uwu.authenticationservice.service.JwtService
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

            val authHeader: String? = request.getHeader("Authorization")
            if (authHeader == null) {
                filterChain.doFilter(request, response);
                return
            }
            if (authHeader == "" || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return
            }

            val jwt = authHeader.substring(7)
            logger.debug(request.headerNames)
            val userEmail = jwtService.extractUsername(jwt)

            if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = this.userDetailsService.loadUserByUsername(userEmail)
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(userEmail, null, userDetails.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
            filterChain.doFilter(request, response)
        }
}