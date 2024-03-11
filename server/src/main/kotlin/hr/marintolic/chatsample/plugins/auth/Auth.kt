package hr.marintolic.chatsample.plugins.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import hr.marintolic.chatsample.EnvironmentVariables
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

/**
 * Installs and sets up authentication.
 */
internal fun Application.installAuthenticationPlugin() {
    install(Authentication) {
        jwtAuth()
    }
}

/**
 * Sets up JWT authentication.
 */
private fun AuthenticationConfig.jwtAuth() {
    jwt("auth-jwt") {
        realm = EnvironmentVariables.jwtRealm

        verifier(
            JWT.require(Algorithm.HMAC256(EnvironmentVariables.jwtSecret))
                .withAudience(EnvironmentVariables.jwtAudience)
                .withIssuer(EnvironmentVariables.jwtIssuer)
                .build()
        )

        // Responds to requests without a token or containing bad ones
        challenge { _, _ ->
            call.respond(HttpStatusCode.Unauthorized, "Invalid or expired token.")
        }

        // Validates JWT tokens
        validate { credential ->
            if (credential.payload.getClaim("username").asString() != "") {
                JWTPrincipal(credential.payload)
            } else {
                null
            }
        }
    }
}