package com.example.nytimes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.ui.SearchActivity
import com.example.nytimes.ui.ViewNewsActivity
import com.example.nytimes.ui.adapter.SectionAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "NYT"

        // List of sections
        val sections = listOf("Search", "viewed", "emailed", "shared")
        val sectionsRecyclerView: RecyclerView = findViewById(R.id.sections_recycler_view)

        // Set up RecyclerView
        sectionsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SectionAdapter(this, sections) { sectionName ->
            if (sectionName == "Search") {
                startActivity(Intent(this, SearchActivity::class.java))
            } else {
                val intent = Intent(this, ViewNewsActivity::class.java).apply {
                    putExtra("section", sectionName)
                }
                startActivity(intent)
            }
        }
        sectionsRecyclerView.adapter = adapter
    }
}
