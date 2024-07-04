package com.uwu.migrationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MigrationServiceApplication

fun main(args: Array<String>) {
	runApplication<MigrationServiceApplication>(*args)
}
