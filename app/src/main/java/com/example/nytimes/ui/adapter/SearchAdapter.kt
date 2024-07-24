package com.example.nytimes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.R
import com.example.nytimes.dao.SearchArticle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchAdapter(private val articles: MutableList<SearchArticle>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
    private val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.headline.main
        holder.publishedDateTextView.text = formatPublishedDate(article.pub_date)
    }

    override fun getItemCount(): Int = articles.size

    fun addArticles(newArticles: List<SearchArticle>) {
        articles.addAll(newArticles)
        articles.sortByDescending { parseDate(it.pub_date) }
        notifyDataSetChanged()
    }

    private fun formatPublishedDate(dateString: String): String {
        return try {
            val date = inputDateFormat.parse(dateString)
            if (date != null) {
                outputDateFormat.format(date)
            } else {
                "Unknown date"
            }
        } catch (e: Exception) {
            "Unknown date: ${e.message}"
        }
    }

    private fun parseDate(dateString: String): Date? {
        return try {
            inputDateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val publishedDateTextView: TextView = itemView.findViewById(R.id.published_date)
    }
}
