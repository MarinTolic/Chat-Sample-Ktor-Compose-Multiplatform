package ui.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import data.model.User
import io.ktor.client.statement.*
import networking.login.LoginApiImpl

/**
 * A ViewModel designed to handle user login.
 */
internal actual class LoginViewModel : ViewModel(), LoginViewModelContract {

    /**
     * Handles user login.
     */
    private val loginApiImpl = LoginApiImpl()

    /**
     * Logs the user in and returns a JWT.
     *
     * @param username The username of the user to be logged in
     * @param password The password of the user to be logged in
     *
     * @return The user's JWT.
     */
    override suspend fun login(username: String, password: String): HttpResponse =
        loginApiImpl.login(User(username, password))

    companion object {

        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel()
            }
        }
    }
}