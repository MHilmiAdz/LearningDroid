package com.example.learningdroid.frost.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "chat_table")
@Parcelize
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: Int, // Links to ChatSession.id
    val text: String,
    val isUser: Boolean,
    val date: String
) : Parcelable