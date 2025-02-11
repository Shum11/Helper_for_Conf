package com.example.helper_for_conf.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.helper_for_conf.models.Ingredient

@Dao
interface IngredientDao {

    @Insert
    suspend fun insert(ingredient: Ingredient)

    @Update
    suspend fun update(ingredient: Ingredient)

    @Query("DELETE FROM ingredients WHERE id = :ingredientId")
    suspend fun delete(ingredientId: Long) // Принимаем ID ингредиента

    @Query("SELECT * FROM ingredients WHERE chapterId = :chapterId")
    fun getIngredientsByChapterId(chapterId: Long): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    fun getIngredientById(ingredientId: Long): LiveData<Ingredient>
}