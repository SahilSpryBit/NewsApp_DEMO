package com.example.newsapp2

import android.app.Application
import androidx.room.Room
import com.example.newsapp2.database.NewsRoomDatabase

class MyApplication : Application() {
    companion object {
        lateinit var database: NewsRoomDatabase
        var fromRoom: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, NewsRoomDatabase::class.java, "News_db").build()
    }
}