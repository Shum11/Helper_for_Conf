package com.example.helper_for_conf.activities.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helper_for_conf.R
import com.example.helper_for_conf.adapters.ChapterAdapter
import com.example.helper_for_conf.databinding.ActivityRecipeDetailBinding
import com.example.helper_for_conf.models.Chapter
import com.example.helper_for_conf.models.Recipe
import com.example.helper_for_conf.utils.ImageUtils
import com.example.helper_for_conf.viewmodels.RecipeViewModel

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var chapterAdapter: ChapterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Кнопка "Назад"

        // Получение ID рецепта из Intent
        val recipeId = intent.getLongExtra("recipe_id", -1)
        if (recipeId == -1L) {
            Log.e("RecipeDetailActivity", "Invalid recipe ID")
            finish() // Закрываем активность, если ID не передан
            return
        }

        // Загрузка данных рецепта
        viewModel.getRecipeById(recipeId).observe(this) { recipe ->
            if (recipe != null) {
                // Устанавливаем название рецепта в Toolbar
                supportActionBar?.title = recipe.name

                // Отображаем описание рецепта
                binding.recipeDescription.text = recipe.description

                // Загрузка изображения с помощью ImageUtils
                recipe.imageUri?.let { uri ->
                    ImageUtils.loadImage(
                        this,
                        uri,
                        binding.recipeImage,
                        R.drawable.placeholder_image,
                        R.drawable.error_image
                    )
                }
            } else {
                Log.e("RecipeDetailActivity", "Recipe not found")
                finish() // Закрываем активность, если рецепт не найден
            }
        }

        // Инициализация RecyclerView для глав
        chapterAdapter = ChapterAdapter(emptyList()) { chapter ->
            // Обработка клика на главу (например, переход к редактированию главы)
            val intent = Intent(this, ChapterEditActivity::class.java)
            intent.putExtra("chapter_id", chapter.id)
            startActivity(intent)
        }

        binding.chapterRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chapterRecyclerView.adapter = chapterAdapter

        // Загрузка данных глав
        loadChapters()

        // Обработка нажатия на кнопку "Добавить главу"
        binding.btnAddChapter.setOnClickListener {
            val intent = Intent(this, ChapterEditActivity::class.java)
            intent.putExtra("recipe_id", recipeId)
            startActivity(intent)
        }
    }

    private fun loadChapters() {
        val recipeId = intent.getLongExtra("recipe_id", -1)
        if (recipeId != -1L) {
            viewModel.getChaptersByRecipeId(recipeId).observe(this) { chapters ->
                Log.d("RecipeDetailActivity", "Chapters loaded: ${chapters.size}")
                chapterAdapter.updateChapters(chapters)
            }
        } else {
            Log.e("RecipeDetailActivity", "Invalid recipe ID")
        }
    }

    // Создание меню с кнопками редактирования и удаления
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Закрываем активность при нажатии на кнопку "Назад"
                true
            }
            R.id.action_edit -> {
                // Переход к редактированию рецепта
                val recipeId = intent.getLongExtra("recipe_id", -1)
                if (recipeId != -1L) {
                    val intent = Intent(this, RecipeEditActivity::class.java)
                    intent.putExtra("recipe_id", recipeId)
                    startActivity(intent)
                }
                true
            }
            R.id.action_delete -> {
                // Показываем диалог подтверждения удаления
                showDeleteConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteConfirmationDialog() {
        val recipeId = intent.getLongExtra("recipe_id", -1)
        if (recipeId == -1L) return

        AlertDialog.Builder(this)
            .setTitle("Удаление рецепта")
            .setMessage("Вы уверены, что хотите удалить этот рецепт?")
            .setPositiveButton("Удалить") { _, _ ->
                // Удаляем рецепт, если пользователь подтвердил
                viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                    if (recipe != null) {
                        viewModel.delete(recipe)
                        finish() // Закрываем активность после удаления
                    }
                }
            }
            .setNegativeButton("Отмена", null) // Ничего не делаем, если пользователь отменил
            .create()
            .show()
    }
}