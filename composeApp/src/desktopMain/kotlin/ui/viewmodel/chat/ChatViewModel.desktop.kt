package ui.viewmodel.chat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.model.ChatMessage
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import networking.chat.ChatApi

/**
 * A ViewModel designed to handle the chat screen.
 */
internal actual class ChatViewModel : ChatApi {

    /**
     * The coroutine scope used for running coroutines.
     */
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    /**
     * The default chat WebSocket connection.
     */
    private lateinit var defaultWebSocketSession: DefaultWebSocketSession

    /**
     * The list of chat messages, in mutable form.
     */
    private val _messages: MutableState<List<ChatMessage>> = mutableStateOf(listOf())

    /**
     * The list of chat messages.
     */
    actual val messages: State<List<ChatMessage>> = _messages

    /**
     * Initializes the WebSocket session.
     *
     * @param jwt The user's token used to initialize the session.
     */
    actual suspend fun initializeChatWebSocketSession(jwt: String) {
        defaultWebSocketSession = connectToChatWebSocket(jwt)
        receiveMessages()
    }

    /**
     * Receives messages from the server.
     */
    actual suspend fun receiveMessages() {
        coroutineScope.launch {
            while (true) {
                val incomingMessage = defaultWebSocketSession.incoming.receive() as? Frame.Text? ?: continue
                val message = incomingMessage.readText()

                val newMessageList = mutableListOf(ChatMessage(message = message, isMine = false))
                newMessageList.addAll(messages.value)

                _messages.value = newMessageList
            }
        }
    }

    /**
     * Sends a message to the chat server.
     *
     * @param message The message to be sent.
     */
    actual suspend fun sendMessage(message: String) {
        val newMessageList = mutableListOf(ChatMessage(message = message, isMine = true))
        newMessageList.addAll(messages.value)

        _messages.value = newMessageList

        defaultWebSocketSession.send(message)
    }
}