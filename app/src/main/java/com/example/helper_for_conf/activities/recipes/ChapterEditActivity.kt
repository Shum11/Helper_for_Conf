package com.example.helper_for_conf.activities.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.helper_for_conf.databinding.ActivityChapterEditBinding
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.viewmodels.ChapterViewModel

class ChapterEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChapterEditBinding
    private val viewModel: ChapterViewModel by viewModels()
    private var currentChapter: Chapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityChapterEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение recipeId из Intent
        val recipeId = intent.getLongExtra("recipe_id", -1)
        if (recipeId == -1L) {
            Log.e("ChapterEditActivity", "Invalid recipe ID")
            finish()
            return
        }

        Log.d("ChapterEditActivity", "Received recipeId: $recipeId")

        binding.btnAddIngredient.setOnClickListener {
            val chapterId = currentChapter?.id ?: -1
            if (chapterId != -1L) {
                Log.d("ChapterEditActivity", "Adding ingredient to chapter: $chapterId")
                val intent = Intent(this, IngredientEditActivity::class.java)
                intent.putExtra("chapter_id", chapterId)
                startActivity(intent)
            } else {
                Log.e("ChapterEditActivity", "Invalid chapter ID")
            }
        }

        // Получение данных из Intent
        val chapterId = intent.getLongExtra("chapter_id", -1)

        if (chapterId != -1L) {
            // Режим редактирования
            viewModel.getChapterById(chapterId).observe(this) { chapter ->
                if (chapter != null) {
                    currentChapter = chapter
                    binding.etChapterName.setText(chapter.name)
                    binding.etChapterDescription.setText(chapter.description)
                }
            }
        } else {
            // Режим создания новой главы
            currentChapter = Chapter(
                id = 0,
                recipeId = recipeId,
                name = "",
                description = ""
            )
        }

        // Обработка нажатия на кнопку "Сохранить"
        binding.btnSave.setOnClickListener {
            val name = binding.etChapterName.text.toString()
            val description = binding.etChapterDescription.text.toString()

            if (name.isNotEmpty()) {
                val chapter = currentChapter?.copy(
                    name = name,
                    description = description
                ) ?: Chapter(
                    id = 0,
                    recipeId = recipeId, // Используем recipeId
                    name = name,
                    description = description
                )

                Log.d("ChapterEditActivity", "Saving chapter: ${chapter.name}, recipeId: ${chapter.recipeId}")
                if (currentChapter == null) {
                    // Создание новой главы
                    viewModel.insert(chapter)
                } else {
                    // Обновление существующей главы
                    viewModel.update(chapter)
                }

                // Закрытие активности
                finish()
            } else {
                // Показать ошибку, если название не заполнено
                binding.etChapterName.error = "Название главы обязательно"
            }
        }
    }
}