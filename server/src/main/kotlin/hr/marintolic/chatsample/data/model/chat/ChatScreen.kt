package hr.marintolic.chatsample.data.model.chat

import hr.marintolic.chatsample.database.user.UserDatabase
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

/**
 * Creates a chat screen for the user defined by the extension function receiver.
 *
 * @receiver The user for whom the chat screen data is created.
 *
 * @return The Chat screen prepared for the user defined by the username [this].
 */
internal fun String.createChatScreen(): ChatScreen {
    val currentChatUser = ChatUser(username = this)

    val otherUsers = UserDatabase.getAllOtherUsers(username = this)
        .map { user -> ChatUser(username = user.username) }

    return ChatScreen(
        currentUser = currentChatUser,
        otherUsers = otherUsers
    )
}