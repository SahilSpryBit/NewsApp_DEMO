package com.example.newsapp2.network

import com.example.newsapp2.model.NewsResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("everything")
    fun getNews(
        @Query("q") query: String?,
        @Query("apiKey") apiKey: String?,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?
    ): Call<NewsResponse?>?
}