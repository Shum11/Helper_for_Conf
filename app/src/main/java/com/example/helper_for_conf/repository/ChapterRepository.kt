package com.example.helper_for_conf.repository

import androidx.lifecycle.LiveData
import com.example.helper_for_conf.database.ChapterDao
import com.example.helper_for_conf.database.IngredientDao
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChapterRepository(private val chapterDao: ChapterDao, private val ingredientDao: IngredientDao) {

    // Добавление главы
    suspend fun insertChapter(chapter: Chapter) {
        withContext(Dispatchers.IO) {
            chapterDao.insert(chapter)
        }
    }

    suspend fun updateChapter(chapter: Chapter) {
        withContext(Dispatchers.IO) {
            chapterDao.update(chapter)
        }
    }

    // Получение глав по recipeId
    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>> {
        return chapterDao.getChaptersByRecipeId(recipeId)
    }

    fun getIngredientsByChapterId(chapterId: Long): LiveData<List<Ingredient>> {
        return ingredientDao.getIngredientsByChapterId(chapterId)
    }
}