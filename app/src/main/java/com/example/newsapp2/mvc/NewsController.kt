package com.example.newsapp2.mvc

import com.example.newsapp2.model.NewsResponse
import com.example.newsapp2.model.newsRequest
import com.example.newsapp2.network.ApiInterface
import com.example.newsapp2.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsController {

    private val apiInterface: ApiInterface =
        RetrofitInstance.getRetrofit().create(ApiInterface::class.java)

    fun fetchData(listener: OnDataFetchedListener, newsRequest: newsRequest) {

        apiInterface.getNews(
            newsRequest.q,
            newsRequest.apiKey,
            newsRequest.page,
            newsRequest.pageSize
        )!!.enqueue(object :
            Callback<NewsResponse?> {

            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
                try {

                    if (response.isSuccessful && response.body() != null) {

                        val newsResponse = response.body()
                        listener.onDataFetched(newsResponse!!)
                    } else {
                        listener.onDataFetchError()
                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                    listener.onDataFetchError()
                }
            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                listener.onDataFetchError()
            }


        })

    }

    interface OnDataFetchedListener {
        fun onDataFetched(newsResponse: NewsResponse)
        fun onDataFetchError()
    }
}