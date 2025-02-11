package com.example.helper_for_conf.activities.recipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helper_for_conf.adapters.RecipeAdapter
import com.example.helper_for_conf.databinding.ActivityRecipeListBinding
import com.example.helper_for_conf.models.Recipe
import com.example.helper_for_conf.viewmodels.RecipeViewModel

class RecipeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeListBinding
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация RecyclerView
        recipeAdapter = RecipeAdapter(emptyList()) { recipe ->
            // Обработка клика на рецепт
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipe_id", recipe.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = recipeAdapter

        // Наблюдение за списком рецептов
        viewModel.allRecipes.observe(this) { recipes ->
            recipeAdapter.updateRecipes(recipes)
        }

        // Кнопка добавления нового рецепта
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, RecipeEditActivity::class.java))
        }
    }
}