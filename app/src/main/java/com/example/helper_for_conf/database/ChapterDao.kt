package com.example.helper_for_conf.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.helper_for_conf.models.Chapter

@Dao
interface ChapterDao {

    @Query("SELECT * FROM chapters WHERE recipeId = :recipeId")
    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    fun getChapterById(chapterId: Long): LiveData<Chapter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: Chapter) {
        Log.d("ChapterDao", "Inserting chapter: ${chapter.name}")
        // Room автоматически выполнит вставку
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<Chapter>)

    @Update
    suspend fun update(chapter: Chapter)

    @Query("DELETE FROM chapters WHERE id = :chapterId")
    suspend fun delete(chapterId: Long) // Принимаем ID главы
}