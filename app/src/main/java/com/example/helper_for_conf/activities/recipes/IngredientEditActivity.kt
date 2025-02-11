package com.example.helper_for_conf.activities.recipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.helper_for_conf.R
import com.example.helper_for_conf.databinding.ActivityIngredientEditBinding
import com.example.helper_for_conf.models.Ingredient
import com.example.helper_for_conf.viewmodels.IngredientViewModel

class IngredientEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientEditBinding
    private val viewModel: IngredientViewModel by viewModels()
    private var currentIngredient: Ingredient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityIngredientEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение данных из Intent
        val ingredientId = intent.getLongExtra("ingredient_id", -1)
        val chapterId = intent.getLongExtra("chapter_id", -1)

        if (ingredientId != -1L) {
            // Режим редактирования
            viewModel.getIngredientById(ingredientId).observe(this) { ingredient ->
                if (ingredient != null) {
                    currentIngredient = ingredient
                    binding.etIngredientName.setText(ingredient.name)
                    binding.etIngredientQuantity.setText(ingredient.quantity.toString())
                    binding.spinnerIngredientUnit.setSelection(
                        getUnitIndex(ingredient.unit)
                    )
                }
            }
        } else {
            // Режим создания нового ингредиента
            currentIngredient = Ingredient(
                id = 0,
                chapterId = chapterId,
                name = "",
                quantity = 0.0,
                unit = "гр"
            )
        }

        // Обработка нажатия на кнопку "Сохранить"
        binding.btnSave.setOnClickListener {
            val name = binding.etIngredientName.text.toString()
            val quantity = binding.etIngredientQuantity.text.toString().toDoubleOrNull() ?: 0.0
            val unit = binding.spinnerIngredientUnit.selectedItem as String

            if (name.isNotEmpty()) {
                val ingredient = currentIngredient?.copy(
                    name = name,
                    quantity = quantity,
                    unit = unit
                ) ?: Ingredient(
                    id = 0,
                    chapterId = chapterId,
                    name = name,
                    quantity = quantity,
                    unit = unit
                )

                if (currentIngredient == null) {
                    // Создание нового ингредиента
                    viewModel.insert(ingredient)
                } else {
                    // Обновление существующего ингредиента
                    viewModel.update(ingredient)
                }

                // Закрытие активности
                finish()
            } else {
                // Показать ошибку, если название не заполнено
                binding.etIngredientName.error = "Название ингредиента обязательно"
            }
        }
    }

    // Метод для получения индекса единицы измерения в списке
    private fun getUnitIndex(unit: String): Int {
        val units = resources.getStringArray(R.array.ingredient_units)
        return units.indexOf(unit)
    }
}