package com.example.learningdroid.frost.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChatSession::class, Chat::class], version = 1, exportSchema = false)
abstract class FrostDatabase : RoomDatabase() {

    abstract fun chatSessionDao(): ChatSessionDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: FrostDatabase? = null

        fun getDatabase(context: Context): FrostDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FrostDatabase::class.java,
                    "frost_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}