package com.example.learningdroid.frost.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatSessionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(session: ChatSession): Long

    @Delete
    suspend fun delete(session: ChatSession) // Add this line

    @Query("SELECT * FROM chat_session_table ORDER BY id DESC")
    fun getAllSessions(): LiveData<List<ChatSession>>
}