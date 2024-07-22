package com.uwu.authenticationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = ["com.uwu.migrationservice.entity"])
class AuthenticationServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthenticationServiceApplication>(*args)
}
