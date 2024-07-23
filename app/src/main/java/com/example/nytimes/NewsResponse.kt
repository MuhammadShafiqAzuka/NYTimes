package com.example.nytimes

data class NewsResponse(
    val results: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val published_date: String,
)