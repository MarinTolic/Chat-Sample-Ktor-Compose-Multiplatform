import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.login.LoginScreen


/**
 * The share UI entry point for the app
 */
@Composable
@Preview
fun App() {
    LoginScreen(
        modifier = Modifier.fillMaxSize()
            .background(
                color = MaterialTheme.colors.background
            )
    )
}