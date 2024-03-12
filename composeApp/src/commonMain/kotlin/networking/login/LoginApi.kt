package networking.login

import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import networking.httpClient
import networking.model.request.User
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
    suspend fun login(user: User): HttpResponse =
        httpClient.post(resource = ServerResources.Login()) {
            setBody(user)
        }.call.response
}
