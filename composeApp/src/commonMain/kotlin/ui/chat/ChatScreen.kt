package ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.viewmodel.chat.ChatViewModel

/**
 * Displays the chat screen.
 *
 * @param chatViewModel The ViewModel used to power the screen.
 * @param jwt The user's token.
 */
@Composable
internal fun ChatScreen(
    chatViewModel: ChatViewModel,
    jwt: String
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                MessageComposer(
                    modifier = Modifier.fillMaxHeight(),
                    onSendClicked = {
                        coroutineScope.launch {
                            chatViewModel.sendMessage(it)
                        }
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding() + 10.dp)
                    .requiredSizeIn(maxWidth = 500.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                reverseLayout = true
            ) {
                items(chatViewModel.messages.value) { chatMessage ->
                    ChatBubble(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            ),
                        message = chatMessage.message,
                        messageHorizontalArrangement = if (chatMessage.isMine) Arrangement.End else Arrangement.Start,
                        color = if (chatMessage.isMine) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }


    // Initialize the chat here.
    LaunchedEffect(true) {
        try {
            chatViewModel.initializeChatWebSocketSession(jwt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}