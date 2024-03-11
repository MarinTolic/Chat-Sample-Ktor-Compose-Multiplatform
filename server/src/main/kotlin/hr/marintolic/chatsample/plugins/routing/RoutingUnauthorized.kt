package hr.marintolic.chatsample.plugins.routing

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import hr.marintolic.chatsample.EnvironmentVariables
import hr.marintolic.chatsample.database.user.areCredentialsValid
import hr.marintolic.chatsample.database.user.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


/**
 * The routing for unauthorized users.
 */
internal fun Application.routingUnauthorized() {
    routing {
        homeRoute()
        loginRoute()
    }
}

/**
 * Represents the home route with the url subdirectory [HOME_SUBDIRECTORY].
 */
private fun Routing.homeRoute() {
    get(HOME_SUBDIRECTORY) {
        call.respondText { "Greetings from GDG Osijek!" }
    }
}

/**
 * Represents the login route with the url subdirectory [LOGIN_SUBDIRECTORY].
 */
private fun Routing.loginRoute() {
    post(LOGIN_SUBDIRECTORY) {
        try {
            val user = call.receive<User>()

            if (user.areCredentialsValid()) {
                val jwtToken = JWT.create()
                    .withAudience(EnvironmentVariables.jwtAudience)
                    .withIssuer(EnvironmentVariables.jwtIssuer)
                    .withClaim("username", user.username)
                    .sign(Algorithm.HMAC256(EnvironmentVariables.jwtSecret))

                call.respond(hashMapOf("token" to jwtToken))
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()

            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

/**
 * The subdirectory for the home page.
 */
private const val HOME_SUBDIRECTORY = "/"

/**
 * The subdirectory for the login page.
 */
private const val LOGIN_SUBDIRECTORY = "/login"