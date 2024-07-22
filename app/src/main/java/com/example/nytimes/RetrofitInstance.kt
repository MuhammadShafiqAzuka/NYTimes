package com.example.nytimes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsApi: NewsApiService = retrofit.create(NewsApiService::class.java)
}
