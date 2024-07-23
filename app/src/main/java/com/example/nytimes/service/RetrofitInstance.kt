package com.example.nytimes.service

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.nytimes.com/svc/"

    val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val url = request.url
            Log.d("Retrofit", "Request URL: $url")
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsApi = retrofit.create(NewsApiService::class.java)
}