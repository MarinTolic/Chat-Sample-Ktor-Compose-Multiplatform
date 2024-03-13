import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.chat.ChatScreen
import ui.login.LoginScreen
import ui.viewmodel.chat.ChatViewModel
import ui.viewmodel.login.LoginViewModel

/**
 * The share UI entry point for the app
 *
 * @param loginViewModel The ViewModel responsible for logging in Users.
 * @param chatViewModel The ViewModel responsible for displaying the chat page.
 */
@Composable
@Preview
internal fun App(
    loginViewModel: LoginViewModel,
    chatViewModel: ChatViewModel
) {
    // This is not at all an acceptable practice, done purely out of convenience
    // Please never do this in a production app.
    var token by rememberSaveable {
        mutableStateOf("")
    }

    var isChatVisible by remember {
        mutableStateOf(false)
    }

    if (!isChatVisible) {
        LoginScreen(
            loginViewModel = loginViewModel,
            modifier = Modifier.fillMaxSize()
                .background(
                    color = MaterialTheme.colors.background
                )
        ) {
            token = it
            isChatVisible = true
        }
    } else {
        ChatScreen(
            chatViewModel = chatViewModel,
            jwt = token
        )
    }
}