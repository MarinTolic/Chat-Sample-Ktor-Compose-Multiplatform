package ui.viewmodel.chat

import androidx.compose.runtime.State
import data.model.ChatMessage
import networking.chat.ChatApi

/**
 * A ViewModel designed to handle the chat screen.
 */
internal expect class ChatViewModel : ChatApi {

    /**
     * The list of chat messages.
     */
    val messages: State<List<ChatMessage>>

    /**
     * Initializes the WebSocket session.
     *
     * @param jwt The user's token used to initialize the session.
     */
    suspend fun initializeChatWebSocketSession(jwt: String)

    /**
     * Receives messages from the server.
     */
    suspend fun receiveMessages()

    /**
     * Sends a message to the chat server.
     *
     * @param message The message to be sent.
     */
    suspend fun sendMessage(message: String)
}