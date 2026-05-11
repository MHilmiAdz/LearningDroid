package com.example.learningdroid.frost.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningdroid.frost.database.ChatDao
import com.example.learningdroid.frost.ui.chat.ChatViewModel

class ChatViewModelFactory(
    private val chatDao: ChatDao,
    private val sessionId: Int,
    private val aiUrl: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(chatDao, sessionId, aiUrl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}