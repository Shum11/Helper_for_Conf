package com.example.helper_for_conf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chapterId: Long, // ID главы, к которой относится ингредиент
    val name: String,
    val quantity: Double, // Количество (теперь Double)
    val unit: String // Единица измерения (гр, кг, мл, л, ст. л., ч. л., шт)
)