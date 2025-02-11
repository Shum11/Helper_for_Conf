package com.example.helper_for_conf.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.helper_for_conf.database.AppDatabase
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Recipe
import com.example.helper_for_conf.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository

    val allRecipes: LiveData<List<Recipe>>
    private val _selectedRecipe = MutableLiveData<Recipe>()
    val selectedRecipe: LiveData<Recipe> get() = _selectedRecipe

    init {
        val database = AppDatabase.getDatabase(application)
        repository = RecipeRepository(database.recipeDao(), database.chapterDao()) // Передаем оба DAO
        allRecipes = repository.allRecipes
    }

    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) = viewModelScope.launch {
        repository.delete(recipe)
    }

    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
    }

    fun getRecipeById(recipeId: Long): LiveData<Recipe> {
        return repository.getRecipeById(recipeId)
    }

    fun getChaptersByRecipeId(recipeId: Long): LiveData<List<Chapter>> {
        return repository.getChaptersByRecipeId(recipeId)
    }

    fun deleteById(recipeId: Long) = viewModelScope.launch {
        repository.deleteById(recipeId)
    }
}