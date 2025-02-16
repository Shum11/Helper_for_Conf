package com.example.helper_for_conf.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Ingredient
import com.example.helper_for_conf.models.Recipe

@Database(entities = [Ingredient::class, Recipe::class, Chapter::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chapterDao(): ChapterDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                ).fallbackToDestructiveMigration() // Удаляет старую базу данных при изменении схемы
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}