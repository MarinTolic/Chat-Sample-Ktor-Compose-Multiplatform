package ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Displays the message in a chat bubble.
 *
 * @param message The message to be displayed.
 * @param messageHorizontalArrangement The way to arrange/ horizontally bias the message.
 * @param color The color of the message background.
 * @param modifier The modifier used to style the message.
 */
@Composable
internal fun ChatBubble(
    message: String,
    messageHorizontalArrangement: Arrangement.Horizontal,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = messageHorizontalArrangement
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = color,
            elevation = 8.dp
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
                text = message
            )
        }
    }
}