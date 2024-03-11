package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Represents a user's login information.
 *
 * @property username The username a user uses to log-in.
 * @property password The password the user uses to log-in.
 */
@Serializable
internal data class User(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)