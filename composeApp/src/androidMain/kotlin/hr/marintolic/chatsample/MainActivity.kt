package hr.marintolic.chatsample

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ui.viewmodel.login.LoginViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel> { LoginViewModel.factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(loginViewModel = loginViewModel)
        }
    }
}