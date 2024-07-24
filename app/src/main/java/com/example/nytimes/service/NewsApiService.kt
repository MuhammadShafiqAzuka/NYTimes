package com.example.nytimes.service

import com.example.nytimes.dao.NYTSearchResponse
import com.example.nytimes.dao.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("mostpopular/v2/{section}/30.json")
    fun getMostPopularNews(
        @Path("section") section: String,
        @Query("api-key") apiKey: String
    ): Call<NewsResponse>

    @GET("search/v2/articlesearch.json")
    fun searchArticles(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("api-key") apiKey: String
    ): Call<NYTSearchResponse>
}