package ui.viewmodel.login

import io.ktor.client.statement.*


/**
 * A contract any login ViewModel implementation must satisfy.
 */
internal interface LoginViewModelContract {

    /**
     * Logs the user in and returns a JWT.
     *
     * @param username The username of the user to be logged in
     * @param password The password of the user to be logged in
     *
     * @return The user's JWT.
     */
    suspend fun login(username: String, password: String): HttpResponse
}