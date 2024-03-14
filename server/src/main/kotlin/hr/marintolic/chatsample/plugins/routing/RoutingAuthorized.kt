package hr.marintolic.chatsample.plugins.routing

import hr.marintolic.chatsample.data.model.chat.createChatScreen
import hr.marintolic.chatsample.data.model.chat.messages.ChatMessage
import hr.marintolic.chatsample.plugins.auth.getClaimFromPrincipleOrNull
import hr.marintolic.chatsample.plugins.websockets.WebSocketConnection
import hr.marintolic.chatsample.plugins.websockets.webSocketSessionManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json

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
            val username = this.call.getClaimFromPrincipleOrNull("username")
                ?: return@get call.respond(HttpStatusCode.Unauthorized)

            val chatScreen = username.createChatScreen()

            call.respond(chatScreen)
        }
    }
}

/**
 * Represents the WebSocket used for chat communication
 */
internal fun Routing.chatWebSocket() {
    authenticate("auth-jwt") {
        webSocket("/$AUTHORIZED_SUBDIRECTORY/socket") {
            val username = this.call.getClaimFromPrincipleOrNull("username")
                ?: return@webSocket call.respond(HttpStatusCode.Unauthorized)

            webSocketSessionManager.storeConnection(
                WebSocketConnection(
                    username = username,
                    session = this
                )
            )

            for (frame in incoming) {
                try {
                    val incomingFrame = frame as? Frame.Text ?: continue
                    val chatMessage = Json.decodeFromString<ChatMessage>(incomingFrame.readText())

                    webSocketSessionManager.sendSerializedMessageToUsers(
                        message = chatMessage,
                        username = chatMessage.recipient,
                    )
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
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

