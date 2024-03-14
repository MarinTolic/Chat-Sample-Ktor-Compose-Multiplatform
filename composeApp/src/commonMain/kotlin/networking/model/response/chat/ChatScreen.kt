package networking.model.response.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents all the information the user needs to load the chat home page.
 *
 * @property currentUser The currently logged-in user.
 * @property otherUsers The other users the user can chat with.
 */
@Serializable
internal data class ChatScreen(
    @SerialName("current_user")
    val currentUser: ChatUser,
    @SerialName("other_users")
    val otherUsers: List<ChatUser>
)