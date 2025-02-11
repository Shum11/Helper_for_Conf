package com.example.helper_for_conf.database

import androidx.room.TypeConverter
import com.example.helper_for_conf.models.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromIngredientList(ingredients: List<Ingredient>?): String {
        if (ingredients == null) return ""
        val gson = Gson()
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun toIngredientList(ingredientsString: String?): List<Ingredient> {
        if (ingredientsString.isNullOrEmpty()) return emptyList()
        val gson = Gson()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(ingredientsString, type)
    }
}