package com.example.nytimes.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.R
import com.example.nytimes.dao.NewsArticle
import com.example.nytimes.viewmodel.ListViewModel

class ViewNewsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var noArticlesTextView: TextView
    private val articles = mutableListOf<NewsArticle>()
    private var isLoading = false
    private val apiKey = "ZGc5uGXPTZ3noAA5ntzAjL7ZOfqBZCYP"
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_news)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        noArticlesTextView = findViewById(R.id.noArticlesTextView)
        newsAdapter = NewsAdapter(articles)
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Articles"

        // Get the section or query from the Intent
        val section = intent.getStringExtra("section")
        val query = intent.getStringExtra("query")

        if (!query.isNullOrEmpty()) {
            Log.d("ViewNewsActivity", "Search query: $query")
            performSearch(query)
        } else if (!section.isNullOrEmpty()) {
            Log.d("ViewNewsActivity", "News section: $section")
            fetchNews(section)
        } else {
            Log.e("ViewNewsActivity", "No section or query provided")
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItem + 5 >= totalItemCount) {
                    if (!section.isNullOrEmpty()) {
                        fetchNews(section)
                    }
                }
            }
        })

        viewModel.articleResults.observe(this) { response ->
            progressBar.visibility = View.GONE
            response?.results?.let {
                articles.clear()
                articles.addAll(it.sortedByDescending { article -> article.published_date })
                newsAdapter.notifyDataSetChanged()
                noArticlesTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            } ?: run {
                Log.e("ViewNewsActivity", "No articles found")
                noArticlesTextView.visibility = View.VISIBLE
            }
            isLoading = false
        }

        viewModel.errorMessage.observe(this) { error ->
            progressBar.visibility = View.GONE
            isLoading = false
            Log.e("ViewNewsActivity", error)
        }
    }

    private fun fetchNews(section: String) {
        Log.d("ViewNewsActivity", "Fetching news for section: $section")
        isLoading = true
        progressBar.visibility = View.VISIBLE
        noArticlesTextView.visibility = View.GONE
        viewModel.popularArticles(section, apiKey)
    }

    private fun performSearch(query: String) {
        Log.d("ViewNewsActivity", "Performing search for query: $query")
        isLoading = true
        progressBar.visibility = View.VISIBLE
        noArticlesTextView.visibility = View.GONE
        viewModel.searchArticles(query, apiKey)
    }
}