package hr.marintolic.chatsample.plugins.websockets

import io.ktor.server.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Blocking
import java.util.*

/**
 * An instance of the [WebSocketSessionManager].
 */
internal val webSocketSessionManager = WebSocketSessionManager(coroutineScope = CoroutineScope(Dispatchers.IO))

/**
 * A class managing WebSocket sessions.
 *
 * @property coroutineScope The coroutine scope used for work such as automatically pruning closed sessions.
 */
// TODO needs stability testing and possible revising prior to merging, however user persistence should be
// TODO done prior to that so manual testing is easier to perform
internal class WebSocketSessionManager(
    private val coroutineScope: CoroutineScope
) {

    /**
     * A mutable map of all established WebSocket connections.
     */
    private val _sessions: MutableMap<String, MutableList<DefaultWebSocketServerSession>> =
        Collections.synchronizedMap(LinkedHashMap())

    /**
     * A map of all established WebSocket connections.
     */
    internal val sessions: Map<String, List<DefaultWebSocketServerSession>> = _sessions

    /**
     * Retrieves a list of connections established by the user with the username [username].
     *
     * @param username The user with whom the WebSocket connections were established.
     *
     * @return A list of WebSocket sessions initiated by the user with username [username],
     * null or empty if no such session exists.
     */
    internal fun retrieveConnections(username: String): List<DefaultWebSocketServerSession>? =
        _sessions[username]


    /**
     * Checks if an entry with the given key value exists, if not creates one.
     *
     * @param key The given key.
     * @param value The given value.
     *
     * @receiver The map upon which this operation is performed on.
     *
     * @return True if a member had existed previously or a new one was successfully created, false otherwise.
     */
    private fun MutableMap<String, MutableList<DefaultWebSocketServerSession>>.addWebSocketSession(
        key: String,
        value: DefaultWebSocketServerSession
    ): Boolean {
        val doesContainEntry = this.containsKey(key)

        return if (doesContainEntry) {
            this[key]?.add(value)
        } else {
            this[key] = Collections.synchronizedList(mutableListOf())

            this[key]?.add(value)
        } ?: false
    }


    /**
     * Stores the given [WebSocketConnection] in the manager.
     *
     * @param webSocketConnection The WebSocket connection to be stored.
     *
     * @return True if storing the session was successful, false otherwise.
     */
    internal suspend fun storeConnection(webSocketConnection: WebSocketConnection): Boolean {
        coroutineScope.launch {
            removeConnectionOnClose(webSocketConnection)
        }

        return _sessions.addWebSocketSession(
            key = webSocketConnection.username,
            value = webSocketConnection.session
        )
    }

    /**
     * Removes the given [WebSocketConnection] from the manager.
     *
     * @param webSocketConnection The WebSocket connection to be removed.
     *
     * @return True if removing the session was successful, false otherwise.
     */
    private fun removeConnection(webSocketConnection: WebSocketConnection): Boolean =
        _sessions[webSocketConnection.username]?.remove(webSocketConnection.session) ?: false


    /**
     * Removes the given [WebSocketConnection] from the manager automatically after the session closes.
     *
     * @param webSocketConnection The WebSocket connection to be removed automatically when the session closes.
     */
    @Blocking
    internal suspend fun removeConnectionOnClose(webSocketConnection: WebSocketConnection) {
        webSocketConnection.session.closeReason.await()

        webSocketSessionManager.removeConnection(webSocketConnection)
    }

    /**
     * Sends a text message to the user with the username defined by [username].
     * The message will be sent to the user on all connected devices.
     *
     * @param message The message to be sent to the user.
     * @param username The username of the user to whom the message will be sent.
     *
     * @return True if sending the message was successful, false otherwise.
     */
    internal suspend inline fun <reified T> sendSerializedMessageToUsers(
        message: T,
        username: String
    ): Boolean {
        val userSessions = _sessions[username] ?: return false

        userSessions.forEach {
            it.sendSerialized(message)
        }

        return true
    }

}