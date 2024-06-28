package com.uwu.teamupgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class TeamUpGatewayApplication

fun main(args: Array<String>) {
    runApplication<TeamUpGatewayApplication>(*args)
}
