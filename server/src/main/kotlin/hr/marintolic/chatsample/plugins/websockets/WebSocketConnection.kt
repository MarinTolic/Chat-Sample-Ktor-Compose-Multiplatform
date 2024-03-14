package hr.marintolic.chatsample.plugins.websockets

import io.ktor.server.websocket.*

/**
 * Represents a single WebSocket connection. This class is used for managing multiple websocket sessions.
 *
 * @param username The name of the user establishing this connection.
 * @param session The web socket session used to connect to user to the server.
 */
internal data class WebSocketConnection(
    val username: String,
    val session: DefaultWebSocketServerSession,
)