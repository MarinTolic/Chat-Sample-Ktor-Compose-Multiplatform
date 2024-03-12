package hr.marintolic.chatsample.plugins.routing

import hr.marintolic.chatsample.data.model.chat.createChatScreen
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * The routing for authorized users.
 */
internal fun Application.routingAuthorizedUsers() {
    routing {
        chatRoute()
    }
}

/**
 * Represents the chat home page route with the subdirectory [AUTHORIZED_SUBDIRECTORY]/[CHAT_SUBDIRECTORY].
 */
internal fun Routing.chatRoute() {
    authenticate("auth-jwt") {
        get("/$AUTHORIZED_SUBDIRECTORY/$CHAT_SUBDIRECTORY") {
            val principal =
                call.principal<JWTPrincipal>() ?: return@get call.respond(HttpStatusCode.InternalServerError)

            val username = principal.payload.getClaim("username").asString()

            val chatScreen = username.createChatScreen()

            call.respond(chatScreen)
        }
    }
}

/**
 * Represents a subdirectory used for logged-in users.
 */
private const val AUTHORIZED_SUBDIRECTORY = "authorized"

/**
 * Represents a subdirectory used for the chat home page.
 */
private const val CHAT_SUBDIRECTORY = "chat"

