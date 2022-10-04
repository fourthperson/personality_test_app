package com.iak.perstest.data.data_source.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iak.perstest.BuildConfig
import com.iak.perstest.data.entity.PastQuiz

@Database(entities = [PastQuiz::class], version = BuildConfig.VERSION_CODE)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pastQuizDao(): PastQuizDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "pers-test-db")
                .addCallback(object : Callback() {
                }).build()
        }
    }
}