package com.example.helper_for_conf.repository

import androidx.lifecycle.LiveData
import com.example.helper_for_conf.database.ChapterDao
import com.example.helper_for_conf.database.RecipeDao
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Recipe

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val chapterDao: ChapterDao
) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    fun getRecipeById(recipeId: Long): LiveData<Recipe> {
        return recipeDao.getRecipeById(recipeId)
    }

    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>> {
        return chapterDao.getChaptersByRecipeId(recipeId)
    }

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    suspend fun update(recipe: Recipe) {
        recipeDao.update(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    suspend fun deleteById(recipeId: Long) {
        recipeDao.deleteById(recipeId)
    }
}