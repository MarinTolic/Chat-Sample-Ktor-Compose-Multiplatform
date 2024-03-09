package hr.marintolic.chatsample.database.user.model


/**
 * Represents a user's login information.
 *
 * @property username The username a user uses to log-in.
 * @property password The password the user uses to log-in.
 */
internal data class User(
    val username: String,
    val password: String,
)