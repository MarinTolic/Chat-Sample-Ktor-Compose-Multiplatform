package ui.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import networking.login.LoginApi

/**
 * A ViewModel designed to handle user login.
 */
internal actual class LoginViewModel : ViewModel(), LoginApi {

    companion object {

        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel()
            }
        }
    }
}