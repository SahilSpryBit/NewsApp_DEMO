package com.example.newsapp2.dao

import androidx.room.*
import com.example.newsapp2.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Update
    fun updateUser(user: NewsEntity)

    @Query("SELECT * FROM news  WHERE id = :id")
    fun getUserBYId(id: Long): NewsEntity
}