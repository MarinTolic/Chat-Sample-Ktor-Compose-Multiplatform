package hr.marintolic.chatsample.plugins.routing

import hr.marintolic.chatsample.data.model.chat.createChatScreen
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

/**
 * The routing for authorized users.
 */
internal fun Application.routingAuthorizedUsers() {
    routing {
        chatRoute()
        chatWebSocket()
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
 * Represents the WebSocket used for chat communication
 */
internal fun Routing.chatWebSocket(){
    authenticate("auth-jwt") {
        webSocket("/$AUTHORIZED_SUBDIRECTORY/socket") {
            val principal =
                call.principal<JWTPrincipal>() ?: return@webSocket call.respond(HttpStatusCode.InternalServerError)

            val username = principal.payload.getClaim("username").asString()

            send("Hi $username!")

            for (frame in incoming){
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()

                send("Parrot server says: $receivedText")
            }
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

