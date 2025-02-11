package com.example.helper_for_conf.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helper_for_conf.R
import com.example.helper_for_conf.models.Chapter

class ChapterAdapter(
    private var chapters: List<Chapter>,
    private val onItemClick: (Chapter) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chapterName: TextView = itemView.findViewById(R.id.chapterName)
        private val chapterDescription: TextView = itemView.findViewById(R.id.chapterDescription)

        fun bind(chapter: Chapter) {
            chapterName.text = chapter.name
            chapterDescription.text = chapter.description
            itemView.setOnClickListener {
                onItemClick(chapter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chapters[position])
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    fun updateChapters(newChapters: List<Chapter>) {
        Log.d("ChapterAdapter", "Updating chapters: ${newChapters.size}")
        chapters = newChapters
        notifyDataSetChanged()
    }
}