package ui.viewmodel.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import networking.chat.ChatApi

/**
 * A ViewModel designed to handle the chat screen.
 */
internal actual class ChatViewModel : ViewModel(), ChatApi {

    companion object {

        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ChatViewModel()
            }
        }
    }
}