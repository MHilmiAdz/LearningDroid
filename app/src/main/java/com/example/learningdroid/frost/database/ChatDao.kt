package com.example.learningdroid.frost.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(chat: Chat)

    @Query("DELETE FROM chat_table WHERE sessionId = :sessionId")
    suspend fun deleteChatsBySessionId(sessionId: Int)

    @Query("SELECT * FROM chat_table WHERE sessionId = :sessionId ORDER BY id ASC")
    fun getChatsForSession(sessionId: Int): LiveData<List<Chat>>
}