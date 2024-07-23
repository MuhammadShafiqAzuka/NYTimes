package com.example.nytimes

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.nytimes.ui.ViewNewsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "NYT"

        // List of sections
        val sections = listOf("viewed", "emailed", "shared")
        val sectionsListView: ListView = findViewById(R.id.sections_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sections)
        sectionsListView.adapter = adapter

        // Set up item click listener
        sectionsListView.setOnItemClickListener { _, _, position, _ ->
            val sectionName = sections[position]
            val intent = Intent(this, ViewNewsActivity::class.java).apply {
                putExtra("section", sectionName)
            }
            startActivity(intent)
        }
    }
}