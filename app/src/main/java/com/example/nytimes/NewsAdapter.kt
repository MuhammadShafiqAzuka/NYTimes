package com.example.nytimes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val articles: MutableList<NewsArticle>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.publishedDateTextView.text = formatPublishedDate(article.published_date)
    }

    override fun getItemCount(): Int = articles.size

    fun addArticles(newArticles: List<NewsArticle>) {
        articles.addAll(newArticles)
        articles.sortByDescending { parseDate(it.published_date) }
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

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val publishedDateTextView: TextView = itemView.findViewById(R.id.published_date)
    }
}