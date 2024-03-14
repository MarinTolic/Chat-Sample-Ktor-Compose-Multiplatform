import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import ui.viewmodel.chatViewModel
import ui.viewmodel.loginViewModel

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        App(
            loginViewModel = loginViewModel,
            chatViewModel = chatViewModel,
        )
    }
}