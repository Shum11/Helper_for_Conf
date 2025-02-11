package com.example.helper_for_conf.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Ingredient
import com.example.helper_for_conf.models.Recipe

@Database(
    entities = [Recipe::class, Chapter::class, Ingredient::class],
    version = 2, // Увеличьте версию базы данных
    exportSchema = false
)
@TypeConverters(Converters::class) // Добавьте эту аннотацию
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun chapterDao(): ChapterDao
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration() // Опционально: для упрощения миграций
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}