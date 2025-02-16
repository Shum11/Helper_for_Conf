package com.example.helper_for_conf.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Ingredient
import com.example.helper_for_conf.repository.ChapterRepository
import kotlinx.coroutines.launch

class ChapterViewModel(private val repository: ChapterRepository) : ViewModel() {

    // LiveData для списка глав
    lateinit var chapters: LiveData<List<Chapter>>

    // Сохранение главы
    fun insertChapter(chapter: Chapter) = viewModelScope.launch {
        repository.insertChapter(chapter)
    }

    fun updateChapter(chapter: Chapter) = viewModelScope.launch {
        repository.updateChapter(chapter)
    }

    // Загрузка глав по recipeId
    fun loadChaptersByRecipeId(recipeId: Long) {
        chapters = repository.getChaptersByRecipeId(recipeId)
    }

    fun getIngredientsByChapterId(chapterId: Long): LiveData<List<Ingredient>> {
        return repository.getIngredientsByChapterId(chapterId)
    }

}