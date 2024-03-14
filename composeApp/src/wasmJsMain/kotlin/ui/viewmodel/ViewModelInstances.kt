package ui.viewmodel

import ui.viewmodel.chat.ChatViewModel
import ui.viewmodel.login.LoginViewModel

/**
 * An instance of the login ViewModel.
 */
internal val loginViewModel by lazy { LoginViewModel() }

/**
 * An instance of the chat ViewModel
 */
internal val chatViewModel by lazy { ChatViewModel() }