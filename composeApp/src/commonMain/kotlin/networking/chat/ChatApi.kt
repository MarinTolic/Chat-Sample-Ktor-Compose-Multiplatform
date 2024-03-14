package networking.chat

import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import networking.httpClient
import networking.resources.ServerResources

/**
 * An API used to display the chat screen.
 */
internal interface ChatApi {

    /**
     * Gets the [networking.model.response.chat.ChatScreen] information for the user defined by the [jwt].
     *
     * @param jwt The token the currently used in user uses to access the site.
     *
     * @return The response containing the [networking.model.response.chat.ChatScreen] in the body.
     */
    suspend fun getChatScreen(jwt: String): HttpResponse {
        return httpClient.get(resource = ServerResources.Authorized.Chat()) {
            // In a production app this would be configured in the HttpClient via the Authorization plugin
            header(key = "Authorization", value = "Bearer $jwt")
        }
    }

    /**
     * Connects to the chat WebSocket.
     *
     * @param jwt The user's authorization token.
     *
     * @return The default WebSocket session.
     */
    suspend fun connectToChatWebSocket(jwt: String) =
        httpClient.webSocketSession(urlString = "ws://0.0.0.0:8080/authorized/socket"){
            method = HttpMethod.Get
            // In a production app this would be configured in the HttpClient via the Authorization plugin
            header(key = "Authorization", value = "Bearer $jwt")
        }
}