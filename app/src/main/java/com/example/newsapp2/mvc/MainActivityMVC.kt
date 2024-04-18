package com.example.newsapp2.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp2.R
import com.example.newsapp2.adapter.NewsAdapter
import com.example.newsapp2.model.Articles
import com.example.newsapp2.model.NewsResponse
import com.example.newsapp2.model.newsRequest
import com.example.newsapp2.repo.NewsRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.UnknownHostException

class MainActivityMVC : AppCompatActivity(), NewsController.OnDataFetchedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBtn: Button
    private lateinit var searchTxt: TextInputEditText
    var adapter: NewsAdapter? = null
    var currentPage = 1
    var isLoading: Boolean = false
    var mList: ArrayList<Articles> = ArrayList()

    var newsTopic: String? = "latest"

    lateinit var newsController: NewsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mvc)

        recyclerView = findViewById(R.id.recyclerView)
        searchBtn = findViewById(R.id.searchBtn)
        searchTxt = findViewById(R.id.edtsearch)

        newsController = NewsController()
        isInternetAvailable { isAvailable ->
            if (isAvailable) {
                fetchData(newsTopic!!)
            } else {
                Toast.makeText(this, "No Internet!!", Toast.LENGTH_SHORT).show()
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
                        fetchData(newsTopic!!)
                        isLoading = true
                    }
                }
            }
        })

        searchBtn.setOnClickListener {

            if (searchTxt.text!!.isNotEmpty()) {
                currentPage = 1
                mList.clear()
                newsTopic = searchTxt.text.toString()
                fetchData(newsTopic!!)
            }
        }

        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    mList.clear()
                    newsTopic = "latest"
                    fetchData(newsTopic!!)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })

    }

    override fun onDataFetched(newsResponse: NewsResponse) {
        isLoading = false
        for (i in newsResponse.articles.indices) {
            mList.add(newsResponse.articles[i])
        }
        if (adapter == null) {
            adapter = NewsAdapter(this, mList)
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = adapter
        } else {
            adapter!!.notifyAdapter(mList)
        }

    }

    override fun onDataFetchError() {
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

    fun fetchData(newsTopic: String) {

        var newsRequest =
            newsRequest(newsTopic, "a0acb282f2b640ec853cccbaf93ec6e3", currentPage, 10)
        newsController.fetchData(this, newsRequest)
    }

}