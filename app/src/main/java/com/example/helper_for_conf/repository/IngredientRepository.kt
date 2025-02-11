package com.example.helper_for_conf.repository

import androidx.lifecycle.LiveData
import com.example.helper_for_conf.database.IngredientDao
import com.example.helper_for_conf.models.Ingredient

class IngredientRepository(private val ingredientDao: IngredientDao) {

    fun getIngredientsByChapterId(chapterId: Long): LiveData<List<Ingredient>> {
        return ingredientDao.getIngredientsByChapterId(chapterId)
    }

    fun getIngredientById(ingredientId: Long): LiveData<Ingredient> {
        return ingredientDao.getIngredientById(ingredientId)
    }

    suspend fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }

    suspend fun update(ingredient: Ingredient) {
        ingredientDao.update(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        ingredientDao.delete(ingredient.id) // Передаем ID ингредиента
    }
}