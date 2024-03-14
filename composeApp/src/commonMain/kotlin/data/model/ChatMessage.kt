package data.model


/**
 * Represents a chat message.
 *
 * @property message The chat message.
 * @property isMine If the message is the current user's or belongs to another user.
 */
data class ChatMessage(
    val message: String,
    val isMine: Boolean,
)