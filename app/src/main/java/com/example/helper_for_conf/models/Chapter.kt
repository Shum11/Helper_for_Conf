package com.example.helper_for_conf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long, // ID рецепта, к которому относится глава
    val name: String, // Название главы
    val description: String // Описание главы
)