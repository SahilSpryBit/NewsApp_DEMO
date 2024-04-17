package com.example.newsapp2.repo

import androidx.lifecycle.MutableLiveData
import com.example.newsapp2.model.NewsResponse
import com.example.newsapp2.model.newsRequest
import com.example.newsapp2.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    fun newsAPiCall(newsApiResponse: MutableLiveData<NewsResponse>, apiInterface: ApiInterface, newsRequest: newsRequest){

        apiInterface.getNews(newsRequest.q, newsRequest.apiKey, newsRequest.page, newsRequest.pageSize)!!.enqueue(object : Callback<NewsResponse?>{

            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
                try{
                    newsApiResponse.value = response.body()
                }catch (ex : Exception){
                    ex.printStackTrace()
                    newsApiResponse.value = null
                }
            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                newsApiResponse.value = null
            }


        })
    }
}