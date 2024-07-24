package com.example.nytimes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.nytimes.R

class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Search"

        searchView = findViewById(R.id.search_view)
        searchButton = findViewById(R.id.search_buttonn)

        searchButton.setOnClickListener {
            val query = searchView.query.toString()
            if (query.isNotEmpty()) {
                performSearch(query)
            }
        }
    }

    private fun performSearch(query: String) {
        val intent = Intent(this, ViewNewsActivity::class.java)
        intent.putExtra("query", query)
        startActivity(intent)
    }
}