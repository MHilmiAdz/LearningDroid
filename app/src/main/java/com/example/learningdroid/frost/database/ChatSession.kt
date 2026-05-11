package com.example.learningdroid.frost.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "chat_session_table")
@Parcelize
data class ChatSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String
) : Parcelable