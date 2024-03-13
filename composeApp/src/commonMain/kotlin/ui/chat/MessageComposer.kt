package ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chatsample.composeapp.generated.resources.Res
import chatsample.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * used for inputting and sending messages.
 *
 * @param onSendClicked Lambda called when the user clicks on send in order to send a message
 * @param modifier The modifier used to style the composable.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun MessageComposer(
    onSendClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
        )

        Spacer(Modifier.size(10.dp))

        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clickable {
                    onSendClicked(message)
                    message = ""
                },
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )
    }
}
