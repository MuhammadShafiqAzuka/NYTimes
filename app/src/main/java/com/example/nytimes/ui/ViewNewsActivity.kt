package com.example.nytimes.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.dao.NewsArticle
import com.example.nytimes.dao.NewsResponse
import com.example.nytimes.R
import com.example.nytimes.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewNewsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private val articles = mutableListOf<NewsArticle>()
    private var isLoading = false
    private val apiKey = "ZGc5uGXPTZ3noAA5ntzAjL7ZOfqBZCYP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_news)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        newsAdapter = NewsAdapter(articles)
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get the section name from the Intent
        val section = intent.getStringExtra("section")
        if (section != null) {
            Log.d("ViewNewsActivity", "News section: $section")
            getMostPopularNews(section)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItem + 5 >= totalItemCount) {
                    if (section != null) {
                        getMostPopularNews(section)
                    }
                }
            }
        })
    }

    private fun getMostPopularNews(section: String) {
        Log.d("ViewNewsActivity", "News section: $section")
        isLoading = true
        progressBar.visibility = View.VISIBLE

        val call = RetrofitInstance.newsApi.getMostPopularNews(section, apiKey)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    if (newsResponse != null && newsResponse.results.isNotEmpty()) {
                        newsAdapter.addArticles(newsResponse.results)
                    } else {
                        Log.e("Retrofit", "Error: ${response.code()} ${response.message()}")
                    }
                } else {
                    Log.e("Retrofit", "Error: ${response.code()} ${response.message()}")
                }
                isLoading = false
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                isLoading = false
                Log.e("Retrofit", "Error: ${t.message}")
            }
        })
    }

}