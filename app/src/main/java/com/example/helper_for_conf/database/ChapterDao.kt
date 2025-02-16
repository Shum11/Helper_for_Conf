package com.example.helper_for_conf.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.helper_for_conf.models.Chapter

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    fun getChapterById(chapterId: Long): LiveData<Chapter>

    @Insert
    suspend fun insert(chapter: Chapter)

    @Update
    suspend fun update(chapter: Chapter)

    @Query("SELECT * FROM chapters WHERE recipeId = :recipeId")
    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>>
}