package com.example.nytimes.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.nytimes.dao.SearchArticle
import com.example.nytimes.ui.adapter.NewsAdapter
import com.example.nytimes.ui.adapter.SearchAdapter
import com.example.nytimes.viewmodel.ListViewModel

class ViewNewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var noArticlesTextView: TextView
    private val articles = mutableListOf<NewsArticle>()
    private val searchArticles = mutableListOf<SearchArticle>()
    private var isLoading = false
    private val apiKey = "ZGc5uGXPTZ3noAA5ntzAjL7ZOfqBZCYP"
    private val viewModel: ListViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_news)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        noArticlesTextView = findViewById(R.id.noArticlesTextView)
        newsAdapter = NewsAdapter(articles)
        searchAdapter = SearchAdapter(searchArticles)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Articles"

        val section = intent.getStringExtra("section")
        val query = intent.getStringExtra("query")

        recyclerView.layoutManager = LinearLayoutManager(this)

        setupObservers()

        if (!query.isNullOrEmpty()) {
            Log.d("ViewNewsActivity", "Search query: $query")
            recyclerView.adapter = searchAdapter
            performSearch(query)
        } else if (!section.isNullOrEmpty()) {
            Log.d("ViewNewsActivity", "News section: $section")
            recyclerView.adapter = newsAdapter
            fetchNews(section)
        } else {
            Log.e("ViewNewsActivity", "No section or query provided")
            noArticlesTextView.visibility = View.VISIBLE
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItem + 5 >= totalItemCount) {
                    if (!query.isNullOrEmpty()) {
                        performSearch(query)
                    } else if (!section.isNullOrEmpty()) {
                        fetchNews(section)
                    }
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.articleResults.observe(this) { response ->
            handleArticleResponse(response?.results)
        }

        viewModel.searchArticleResults.observe(this) { response ->
            handleSearchResponse(response)
        }

        viewModel.errorMessage.observe(this) { error ->
            progressBar.visibility = View.GONE
            isLoading = false
        }
    }

    private fun handleArticleResponse(articles: List<NewsArticle>?) {
        progressBar.visibility = View.GONE
        articles?.let {
            this.articles.clear()
            newsAdapter.addArticles(it)
            newsAdapter.notifyDataSetChanged()
            noArticlesTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        } ?: run {
            Log.e("ViewNewsActivity", "No articles found")
            noArticlesTextView.visibility = View.VISIBLE
        }
        isLoading = false
    }

    private fun handleSearchResponse(articles: List<SearchArticle>?) {
        progressBar.visibility = View.GONE
        articles?.let {
            searchArticles.clear()
            searchAdapter.addArticles(it)
            searchAdapter.notifyDataSetChanged()
            noArticlesTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        } ?: run {
            Log.e("ViewNewsActivity", "No articles found")
            noArticlesTextView.visibility = View.VISIBLE
        }
        isLoading = false
    }

    private fun fetchNews(section: String) {
        Log.d("ViewNewsActivity", "Fetching news for section: $section")
        isLoading = true
        progressBar.visibility = View.VISIBLE
        noArticlesTextView.visibility = View.GONE
        viewModel.popularArticles(section, apiKey)
    }

    private fun performSearch(query: String) {
        searchRunnable?.let { handler.removeCallbacks(it) }

        searchRunnable = Runnable {
            Log.d("ViewNewsActivity", "Performing search for query: $query")
            isLoading = true
            progressBar.visibility = View.VISIBLE
            noArticlesTextView.visibility = View.GONE
            viewModel.searchArticles(query, apiKey)
        }

        handler.postDelayed(searchRunnable!!, 300)
    }
}