package hr.marintolic.chatsample.plugins.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


/**
 * Fetches the claim from the principle inside the application call.
 *
 * @param claimName The name of the claim.
 *
 * @return The claim from the principal if such exists, null otherwise.
 */
internal fun ApplicationCall.getClaimFromPrincipleOrNull(claimName: String): String? =
    this.principal<JWTPrincipal>()?.payload?.getClaim(claimName)?.asString()
