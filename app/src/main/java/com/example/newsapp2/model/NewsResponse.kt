package com.example.newsapp2.model

import com.example.newsapp2.model.Articles

data class NewsResponse(

    var status: String? = "",
    var totalResults: Int? = 0,
    var articles: ArrayList<Articles> = arrayListOf()

)