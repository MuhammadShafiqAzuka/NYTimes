package com.example.nytimes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("mostpopular/v2/{section}/1.json")
    fun getMostPopularNews(
        @Path("section") section: String,    // Dynamic part of the URL
        @Query("api-key") apiKey: String     // Query parameter for API key
    ): Call<NewsResponse>
}