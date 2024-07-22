package com.example.nytimes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("home.json")
    fun getLatestNews(@Query("api-key") apiKey: String): Call<NewsResponse>
}