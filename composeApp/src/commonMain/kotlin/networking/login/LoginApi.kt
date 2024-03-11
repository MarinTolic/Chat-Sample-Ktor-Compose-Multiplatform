package networking.login

import data.model.User
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import networking.model.JWTResponse
import networking.httpClient
import networking.resources.ServerResources


/**
 * An API dedicated to the user login process.
 */
internal interface LoginApi {

    /**
     * Logs the user in and returns a JWT.
     *
     * @param user The user to be logged in
     *
     * @return The user's JWT.
     */
    suspend fun login(user: User): JWTResponse
}

/**
 * A concrete class used to log users in.
 * The implementation class for [LoginApi].
 */

internal class LoginApiImpl : LoginApi {

    /**
     * Logs the user in and returns a JWT.
     *
     * @param user The user to be logged in
     *
     * @return The user's JWT.
     */
    override suspend fun login(user: User): JWTResponse =
        httpClient.post(resource = ServerResources.Login()) {
            contentType(ContentType.Application.Json)

            setBody(user)
        }.call.response.body()
}
