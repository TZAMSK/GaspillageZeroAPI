package com.GaspillageZeroAPI.GestionAccès

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.util.Assert

class AudienceValidateur(private val clientId: String) : OAuth2TokenValidator<Jwt> {

    init {
        Assert.hasText(clientId, "audience is null or empty")
    }

    override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
        val audiences = jwt.id
        return if (audiences.contains(clientId)) {
            OAuth2TokenValidatorResult.success()
        } else {
            val err = OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN)
            OAuth2TokenValidatorResult.failure(err)
        }
    }
}

