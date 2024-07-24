package com.example.nytimes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.R
import com.google.android.material.card.MaterialCardView

class SectionAdapter(
    private val context: Context,
    private val sections: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sectionIcon: ImageView = view.findViewById(R.id.section_icon)
        val sectionName: TextView = view.findViewById(R.id.section_name)
        private val cardView: MaterialCardView = view.findViewById(R.id.card_view)

        init {
            cardView.setOnClickListener {
                onClick(sections[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val sectionName = sections[position]
        holder.sectionName.text = sectionName
        // Set appropriate icon based on section name
        holder.sectionIcon.setImageResource(
            when (sectionName) {
                "Search" -> R.drawable.ic_search
                "viewed" -> R.drawable.ic_viewed
                "emailed" -> R.drawable.ic_emailed
                "shared" -> R.drawable.ic_shared
                else -> R.drawable.ic_launcher_foreground // Replace with a default drawable
            }
        )
    }

    override fun getItemCount() = sections.size
}
