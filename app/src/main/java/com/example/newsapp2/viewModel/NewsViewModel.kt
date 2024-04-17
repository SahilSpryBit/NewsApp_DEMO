package com.example.newsapp2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp2.MyApplication
import com.example.newsapp2.entity.NewsEntity
import com.example.newsapp2.model.NewsResponse
import com.example.newsapp2.model.newsRequest
import com.example.newsapp2.network.ApiInterface
import com.example.newsapp2.repo.NewsRepository
import com.example.newsapp2.repo.NewsRoomRepo
import kotlinx.coroutines.flow.Flow

class NewsViewModel: ViewModel(){

    var newsResponse = MutableLiveData<NewsResponse>()

    private val newsRoomRepo = NewsRoomRepo(MyApplication.database.newsDao())

    private val _news = MutableLiveData<NewsEntity?>()
    val newsData: MutableLiveData<NewsEntity?>
        get() = _news

    fun callNewsApi(newsRepository: NewsRepository, apiInterface: ApiInterface, newsRequest: newsRequest){

        newsRepository.newsAPiCall(newsResponse, apiInterface, newsRequest)
    }

    fun getNewsFromDatabase(newsRoomRepo: NewsRoomRepo): Flow<List<NewsEntity>> {
        return newsRoomRepo.getAllNews()
    }
}