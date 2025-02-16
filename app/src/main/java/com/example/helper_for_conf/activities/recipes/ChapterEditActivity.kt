package com.example.helper_for_conf.activities.recipes

import ChapterViewModelFactory
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helper_for_conf.adapters.IngredientAdapter
import com.example.helper_for_conf.database.AppDatabase
import com.example.helper_for_conf.databinding.ActivityChapterEditBinding
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.repository.ChapterRepository
import com.example.helper_for_conf.viewmodels.ChapterViewModel

class ChapterEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChapterEditBinding

    // Инициализация базы данных и репозитория
    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    private val repository: ChapterRepository by lazy {
        ChapterRepository(database.chapterDao(), database.ingredientDao())
    }

    private val viewModel: ChapterViewModel by viewModels {
        ChapterViewModelFactory(repository)
    }

    private val recipeId: Long by lazy {
        intent.getLongExtra("recipeId", -1)
    }

    private var chapterId: Long = -1
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChapterEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение ID главы (если редактирование)
        chapterId = intent.getLongExtra("chapter_id", -1)

        ingredientAdapter = IngredientAdapter()
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvIngredients.adapter = ingredientAdapter

        // Загрузка ингредиентов для текущей главы
        if (chapterId != -1L) {
            viewModel.getIngredientsByChapterId(chapterId).observe(this) { ingredients ->
                ingredientAdapter.updateIngredients(ingredients)
            }
        }

        // Кнопка добавления ингредиента
        binding.btnAddIngredient.setOnClickListener {
            val intent = Intent(this, IngredientEditActivity::class.java)
            intent.putExtra("chapter_id", chapterId) // Передайте ID главы
            startActivity(intent)
        }

        binding.btnSaveChapter.setOnClickListener{
            saveChapter()

        }
    }

    private fun saveChapter() {
        val title = binding.etChapterTitle.text.toString()
        val description = binding.etChapterDescription.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val chapter = Chapter(
                id = chapterId,
                title = title,
                description = description,
                recipeId = recipeId,
                ingredients = ingredientAdapter.getIngredients() // Добавьте ингредиенты
            )

            // Сохранение главы через ViewModel
            if (chapterId == -1L) {
                viewModel.insertChapter(chapter)
            } else {
                viewModel.updateChapter(chapter)
            }

            // Закрытие активности после сохранения
            finish()
        } else {
            // Показать сообщение об ошибке, если поля пустые
            binding.etChapterTitle.error = "Заполните поле"
            binding.etChapterDescription.error = "Заполните поле"
        }
    }
}