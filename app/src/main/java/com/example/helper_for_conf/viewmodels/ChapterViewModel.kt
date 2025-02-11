package com.example.helper_for_conf.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.helper_for_conf.database.AppDatabase
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.repository.ChapterRepository
import kotlinx.coroutines.launch

class ChapterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChapterRepository

    init {
        val chapterDao = AppDatabase.getDatabase(application).chapterDao()
        repository = ChapterRepository(chapterDao)
    }

    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>> {
        return repository.getChaptersByRecipeId(recipeId)
    }

    fun getChapterById(chapterId: Long): LiveData<Chapter> {
        return repository.getChapterById(chapterId)
    }

    fun insert(chapter: Chapter) = viewModelScope.launch {
        Log.d("ChapterViewModel", "Calling repository.insert(chapter)")
        repository.insert(chapter)
    }

    fun update(chapter: Chapter) = viewModelScope.launch {
        repository.update(chapter)
    }

    fun delete(chapter: Chapter) = viewModelScope.launch {
        repository.delete(chapter)
    }

    // Метод для загрузки глав (например, из API)
    fun loadChapters(recipeId: Long) {
        viewModelScope.launch {
            repository.loadChapters(recipeId)
        }
    }
}