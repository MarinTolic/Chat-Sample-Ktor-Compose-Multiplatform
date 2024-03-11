import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.viewmodel.loginViewModel

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "ChatSample") {
        App(loginViewModel = loginViewModel)
    }
}