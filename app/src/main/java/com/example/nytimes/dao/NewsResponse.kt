package com.example.nytimes.dao

//Popular
data class NewsResponse(
    val results: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val published_date: String,
)

//Search
data class NYTSearchResponse(
    val response: SearchResponse
)

data class SearchResponse(
    val docs: List<SearchArticle>
)

data class SearchArticle(
    val pub_date: String,
    val headline: Headline
)

data class Headline(
    val main: String
)

