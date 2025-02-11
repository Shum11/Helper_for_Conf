package com.example.helper_for_conf.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.helper_for_conf.database.AppDatabase
import com.example.helper_for_conf.models.Ingredient
import com.example.helper_for_conf.repository.IngredientRepository
import kotlinx.coroutines.launch

class IngredientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IngredientRepository

    init {
        val ingredientDao = AppDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)
    }

    fun getIngredientsByChapterId(chapterId: Long): LiveData<List<Ingredient>> {
        return repository.getIngredientsByChapterId(chapterId)
    }

    fun getIngredientById(ingredientId: Long): LiveData<Ingredient> {
        return repository.getIngredientById(ingredientId)
    }

    fun insert(ingredient: Ingredient) = viewModelScope.launch {
        repository.insert(ingredient)
    }

    fun update(ingredient: Ingredient) = viewModelScope.launch {
        repository.update(ingredient)
    }

    fun delete(ingredient: Ingredient) = viewModelScope.launch {
        repository.delete(ingredient)
    }
}