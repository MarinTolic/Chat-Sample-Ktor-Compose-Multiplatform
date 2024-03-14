package hr.marintolic.chatsample.data.model.chat.messages

import kotlinx.serialization.Serializable

/**
 * Represents a chat message.
 *
 * @param message The chat message.
 * @param sender The username of the user who has sent the message.
 * @param recipient The username of the user to whom the server should send the message.
 */
@Serializable
internal data class ChatMessage(
    val message: String,
    val sender: String,
    val recipient: String,
)
