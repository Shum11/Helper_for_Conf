package com.example.helper_for_conf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val recipeId: Long, // Убедитесь, что этот столбец есть
    val ingredients: List<Ingredient>
)