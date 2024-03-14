package networking.model.response.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Represents a chat user.
 *
 * @property username The username a user uses to log-in.
 */
@Serializable
data class ChatUser(
    @SerialName("username")
    val username: String,
)