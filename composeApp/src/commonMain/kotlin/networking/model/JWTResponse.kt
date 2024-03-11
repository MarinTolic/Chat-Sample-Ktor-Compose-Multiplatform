package networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a successful login response containing the user's JWT.
 *
 * @property token The user's JWT.
 */
@Serializable
data class JWTResponse(
    @SerialName("token")
    val token: String
)