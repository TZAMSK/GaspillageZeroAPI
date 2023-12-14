package com.GaspillageZeroAPI.GestionAccès

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class ServeurRessourcesOAuth2ConfigurationAccès() {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize(HttpMethod.GET, "/**", permitAll)
                authorize(HttpMethod.POST, "/**", authenticated)
                authorize(HttpMethod.PUT, "/**", authenticated)
                authorize(HttpMethod.DELETE, "/**", permitAll)
                authorize(anyRequest, authenticated)
            }

            oauth2Login { withDefaults<Neo4jProperties.Authentication>() }
        }
        return http.build()
    }
}
