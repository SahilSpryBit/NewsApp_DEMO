package com.example.newsapp2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp2.dao.NewsDao
import com.example.newsapp2.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}