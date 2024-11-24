package org.example.project.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import example.com.utils.JWTConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain
    val jwtAudience = JWTConfig.jwtAudience
    val jwtDomain = JWTConfig.jwtDomain
    val jwtRealm = JWTConfig.jwtSecret
    val jwtSecret = JWTConfig.jwtSecret
    install(Authentication) {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }

    }
}