package com.example.nytimes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchLatestNews()
    }

    private fun fetchLatestNews() {
        val call = RetrofitInstance.newsApi.getLatestNews("ZGc5uGXPTZ3noAA5ntzAjL7ZOfqBZCYP")
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    if (newsResponse != null) {
                        newsAdapter = NewsAdapter(newsResponse.results)
                        recyclerView.adapter = newsAdapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to fetch news: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
