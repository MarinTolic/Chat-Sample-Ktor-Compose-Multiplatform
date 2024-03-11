package ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chatsample.composeapp.generated.resources.Res
import chatsample.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.viewmodel.login.LoginViewModel


/**
 * Contains the login screen.
 *
 * @param loginViewModel The ViewModel responsible for logging in Users.
 * @param modifier The modifier used for styling the composable.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onSuccessfulLogin: (token: String) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()

    val username = "Username"

    val password = "Password"

    var title by remember {
        mutableStateOf("")
    }

    var usernameTextFieldValue by rememberSaveable {
        mutableStateOf("")
    }

    var passwordTextFieldValue by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.sizeIn(
                maxWidth = 250.dp,
                maxHeight = 250.dp
            ),
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )

        Text(text = title)

        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(12.dp)
        )

        LoginTextField(
            value = usernameTextFieldValue,
            label = username,
            placeholder = username,
        ) { usernameInput ->
            usernameTextFieldValue = usernameInput
        }

        LoginTextField(
            value = passwordTextFieldValue,
            label = password,
            placeholder = password
        ) { passwordInput ->
            passwordTextFieldValue = passwordInput
        }

        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp)
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    // TODO Log the user in
                }
            }
        ) {
            Text(
                text = "LOGIN",
                style = MaterialTheme.typography.button
            )
        }
    }
}

/**
 * A stylized TextField used for the login screen.
 *
 * @param value The input text to be shown in the text field
 * @param onValueChange The callback that is triggered when the input service updates the text.
 * An updated text comes as a parameter of the callback
 */
@Composable
private fun LoginTextField(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background
        ),
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        }
    )
}

