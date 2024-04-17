package com.example.newsapp2.repo

import com.example.newsapp2.dao.NewsDao
import com.example.newsapp2.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

class NewsRoomRepo(private val newsDao: NewsDao) {

    fun getAllNews(): Flow<List<NewsEntity>> {
        return newsDao.getAllNews()
    }
}