package com.example.nytimes.service

import com.example.nytimes.dao.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("mostpopular/v2/{section}/30.json")
    fun getMostPopularNews(
        @Path("section") section: String,    // Dynamic part of the URL
        @Query("api-key") apiKey: String     // Query parameter for API key
    ): Call<NewsResponse>

    @GET("search/v2/articlesearch.json")
    fun searchArticles(
        @Query("q") query: String,           // Query parameter for search text
        @Query("api-key") apiKey: String     // Query parameter for API key
    ): Call<NewsResponse>
}