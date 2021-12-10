package com.devansab.major1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devansab.major1.data.daos.LastMessageDao
import com.devansab.major1.data.daos.MessageDao
import com.devansab.major1.data.daos.UserDao
import com.devansab.major1.data.entities.LastMessage
import com.devansab.major1.data.entities.Message
import com.devansab.major1.data.entities.User

@Database(entities = [LastMessage::class, User::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun lastMessageDao(): LastMessageDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "major1_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}