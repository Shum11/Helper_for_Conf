package com.example.helper_for_conf.activities.recipes

import ChapterViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helper_for_conf.R
import com.example.helper_for_conf.adapters.ChapterAdapter
import com.example.helper_for_conf.database.AppDatabase
import com.example.helper_for_conf.databinding.ActivityRecipeDetailBinding
import com.example.helper_for_conf.models.Recipe
import com.example.helper_for_conf.repository.ChapterRepository
import com.example.helper_for_conf.viewmodels.ChapterViewModel
import com.example.helper_for_conf.viewmodels.RecipeViewModel

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

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

    private val recipeViewModel: RecipeViewModel by viewModels()

    private val recipeId: Long by lazy {
        intent.getLongExtra("recipeId", -1)
    }

    private lateinit var chapterAdapter: ChapterAdapter

    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Настройка Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Кнопка "Назад"

        // Загрузка глав для текущего рецепта
        viewModel.loadChaptersByRecipeId(recipeId)

        // Включите меню в тулбаре
        setSupportActionBar(binding.toolbar)

        // Инициализация адаптера
        chapterAdapter = ChapterAdapter()
        binding.rvChapters.apply {
            layoutManager = LinearLayoutManager(this@RecipeDetailActivity)
            adapter = chapterAdapter
        }

        // Загрузка глав по recipeId
        viewModel.loadChaptersByRecipeId(recipeId)

        // Подписка на LiveData
        observeChapters()

        // Кнопка добавления главы
        binding.btnAddChapter.setOnClickListener {
            openChapterEditActivity()
        }
    }


    private fun observeChapters() {
        viewModel.chapters.observe(this, Observer { chapters ->
            chapterAdapter.updateChapters(chapters)
        })
    }

    private fun openChapterEditActivity() {
        val intent = Intent(this, ChapterEditActivity::class.java)
        intent.putExtra("recipeId", recipeId)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                // Открыть экран редактирования рецепта
                true
            }
            R.id.action_delete -> {
                // Удалить рецепт
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}