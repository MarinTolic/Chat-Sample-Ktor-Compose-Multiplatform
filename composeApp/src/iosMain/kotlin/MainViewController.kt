import androidx.compose.ui.window.ComposeUIViewController
import ui.viewmodel.chatViewModel
import ui.viewmodel.loginViewModel

fun MainViewController() = ComposeUIViewController {
    App(
        loginViewModel = loginViewModel,
        chatViewModel = chatViewModel,
    )
}