package com.uwu.authenticationservice.configuration

import com.uwu.authenticationservice.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .addFilterBefore(JwtAuthenticationFilter(userDetailsService, jwtService), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                        "/api/authentication/email/**",
                        "/api/authentication/who-am-i"
                    )
                    .authenticated()
                    .anyRequest()
                    .permitAll()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement { httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
        return http.build()
    }

}