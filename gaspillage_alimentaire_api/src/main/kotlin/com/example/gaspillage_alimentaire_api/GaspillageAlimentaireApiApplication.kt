package com.example.gaspillage_alimentaire_api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition
class GaspillageAlimentaireApiApplication

fun main(args: Array<String>) {
	runApplication<GaspillageAlimentaireApiApplication>(*args)
}
