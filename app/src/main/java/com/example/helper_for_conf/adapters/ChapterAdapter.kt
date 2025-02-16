package com.example.helper_for_conf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helper_for_conf.R
import com.example.helper_for_conf.models.Chapter

class ChapterAdapter : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    private var chapters: List<Chapter> = emptyList()

    inner class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvChapterTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvChapterDescription)

        fun bind(chapter: Chapter) {
            tvTitle.text = chapter.title
            tvDescription.text = chapter.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(chapters[position])
    }

    override fun getItemCount(): Int = chapters.size

    fun updateChapters(chapters: List<Chapter>) {
        this.chapters = chapters
        notifyDataSetChanged()
    }
}