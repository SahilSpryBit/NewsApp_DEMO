package com.example.newsapp2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp2.MyApplication
import com.example.newsapp2.R
import com.example.newsapp2.adapter.NewsAdapter
import com.example.newsapp2.adapter.NewsAdapter2
import com.example.newsapp2.entity.NewsEntity
import com.example.newsapp2.model.Articles
import com.example.newsapp2.model.NewsResponse
import com.example.newsapp2.model.newsRequest
import com.example.newsapp2.network.ApiInterface
import com.example.newsapp2.network.RetrofitInstance
import com.example.newsapp2.repo.NewsRepository
import com.example.newsapp2.repo.NewsRoomRepo
import com.example.newsapp2.viewModel.NewsViewModel
import kotlinx.coroutines.*
import java.net.InetAddress
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var apiInterface: ApiInterface
    private lateinit var recyclerView: RecyclerView
    var adapter: NewsAdapter? = null
    var currentPage = 1
    var isLoading: Boolean = false
    var mList: ArrayList<Articles> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("Sahilll", "ONCREATEEEEEEEEEE")

        recyclerView = findViewById(R.id.recyclerView)

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface::class.java)

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel.newsResponse.observe(this, newsObserver)

        isInternetAvailable { isAvailable ->
            if (isAvailable) {
                fetchData()
            } else {
                lifecycleScope.launch {

                    val newsDao = MyApplication.database.newsDao()
                    val newsRoomRepo = NewsRoomRepo(newsDao)

                    newsViewModel.getNewsFromDatabase(newsRoomRepo).collect { news ->
                        if (news.isNotEmpty()) {
                            val adapter2 = NewsAdapter2(this@MainActivity, news)
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            recyclerView.adapter = adapter2
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "No Data Cached!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mList!!.size - 1) {
                        //bottom of list!
                        currentPage++
                        fetchData()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun fetchData() {
        var newsRequest =
            newsRequest("bitcoin", "a0acb282f2b640ec853cccbaf93ec6e3", currentPage, 10)

        newsViewModel.callNewsApi(NewsRepository(), apiInterface, newsRequest)
    }

    //API response
    private var newsObserver: Observer<NewsResponse?> = Observer { response ->

        try {

            if (response == null) {
                Toast.makeText(this, "Data NUlllll ", Toast.LENGTH_SHORT).show()
            } else {
                if (response.articles.size > 0) {

                    Log.e("Sahilll", "Dataa :: " + response.articles)

                    isLoading = false
                    val news = response.articles.mapIndexed { index, news ->
                        mList.add(response.articles[index])
                        NewsEntity(
                            index.toLong(),
                            news.title,
                            news.description,
                            news.content,
                            news.author,
                            news.publishedAt,
                            news.urlToImage
                        )
                    }
                    GlobalScope.launch(Dispatchers.IO) {
                        MyApplication.database.newsDao().insert(news)
                    }
                    if (adapter == null) {
                        adapter = NewsAdapter(this@MainActivity, mList)
                        recyclerView!!.layoutManager = LinearLayoutManager(this)
                        recyclerView!!.adapter = adapter
                    } else {
                        adapter!!.notifyAdapter(mList)
                    }

                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun isInternetAvailable(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                val address = InetAddress.getByName("www.google.com")
                address.isReachable(500)
            } catch (e: UnknownHostException) {
                false
            }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }
}