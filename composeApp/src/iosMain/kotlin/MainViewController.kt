import androidx.compose.ui.window.ComposeUIViewController
import ui.viewmodel.loginViewModel

fun MainViewController() = ComposeUIViewController { App(loginViewModel = loginViewModel) }