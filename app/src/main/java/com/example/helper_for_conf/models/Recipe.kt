package com.example.helper_for_conf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val imageUri: String?,
    val ingredients: List<Ingredient> // Используйте List<Ingredient>
)