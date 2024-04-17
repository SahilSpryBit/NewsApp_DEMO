package com.example.newsapp2.model

data class newsRequest(
    var q: String? = null,
    var apiKey: String? = null,
    var page: Int? = null,
    var pageSize: Int? = null
)
