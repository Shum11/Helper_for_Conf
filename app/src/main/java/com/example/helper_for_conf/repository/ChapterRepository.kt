package com.example.helper_for_conf.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.helper_for_conf.database.ChapterDao
import com.example.helper_for_conf.models.Chapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChapterRepository(private val chapterDao: ChapterDao) {

    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>> {
        return chapterDao.getChaptersByRecipeId(recipeId)
    }

    fun getChapterById(chapterId: Long): LiveData<Chapter> {
        return chapterDao.getChapterById(chapterId)
    }

    suspend fun insert(chapter: Chapter) {
        Log.d("ChapterRepository", "Calling chapterDao.insert(chapter)")
        chapterDao.insert(chapter)
    }

    suspend fun update(chapter: Chapter) {
        chapterDao.update(chapter)
    }

    suspend fun delete(chapter: Chapter) {
        chapterDao.delete(chapter.id) // Передаем ID главы
    }

    suspend fun loadChapters(recipeId: Long) {
        withContext(Dispatchers.IO) {
            // Заглушка: здесь должен быть вызов API
            val chaptersFromApi = fetchChaptersFromApi(recipeId)

            // Сохраняем данные в базу данных
            chapterDao.insertAll(chaptersFromApi)
        }
    }

    private suspend fun fetchChaptersFromApi(recipeId: Long): List<Chapter> {
        // Заглушка: здесь должен быть вызов API
        return emptyList()
    }
}