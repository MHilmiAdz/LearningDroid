package com.example.learningdroid.frost.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.learningdroid.frost.database.ChatSession

class ChatSessionDiffCallback : DiffUtil.ItemCallback<ChatSession>() {
    override fun areItemsTheSame(oldItem: ChatSession, newItem: ChatSession): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatSession, newItem: ChatSession): Boolean {
        return oldItem == newItem
    }
}