package com.example.helper_for_conf.activities.recipes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.helper_for_conf.databinding.ActivityRecipeEditBinding
import com.example.helper_for_conf.models.Recipe
import com.example.helper_for_conf.viewmodels.RecipeViewModel

class RecipeEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeEditBinding
    private val viewModel: RecipeViewModel by viewModels()
    private var currentRecipe: Recipe? = null
    private var selectedImageUri: Uri? = null

    // Регистрируем ActivityResultLauncher для выбора изображения
    private val selectImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                // Запрашиваем постоянные разрешения на доступ к файлу
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                selectedImageUri = uri
                binding.recipeImage.setImageURI(uri) // Показываем выбранное изображение
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityRecipeEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение ID рецепта из Intent
        val recipeId = intent.getLongExtra("recipe_id", -1)

        if (recipeId != -1L) {
            // Режим редактирования
            viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                if (recipe != null) {
                    currentRecipe = recipe
                    binding.etRecipeName.setText(recipe.name)
                    binding.etRecipeDescription.setText(recipe.description)

                    // Загрузка изображения, если оно есть
                    recipe.imageUri?.let { uri ->
                        selectedImageUri = Uri.parse(uri)
                        binding.recipeImage.setImageURI(selectedImageUri)
                    }
                }
            }
        }

        // Обработка нажатия на кнопку "Выбрать фото"
        binding.btnSelectImage.setOnClickListener {
            openImagePicker()
        }

        // Обработка нажатия на кнопку "Сохранить"
        binding.btnSave.setOnClickListener {
            val name = binding.etRecipeName.text.toString()
            val description = binding.etRecipeDescription.text.toString()

            if (name.isNotEmpty()) {
                val recipe = Recipe(
                    id = currentRecipe?.id ?: 0,
                    name = name,
                    description = description,
                    imageUri = selectedImageUri?.toString(),
                    ingredients = currentRecipe?.ingredients ?: emptyList() // Используем существующие ингредиенты или пустой список
                )

                if (currentRecipe == null) {
                    // Создание нового рецепта
                    viewModel.insert(recipe)
                } else {
                    // Обновление существующего рецепта
                    viewModel.update(recipe)
                }

                // Закрытие активности
                finish()
            } else {
                // Показать ошибку, если название не заполнено
                binding.etRecipeName.error = "Название рецепта обязательно"
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        selectImageLauncher.launch(intent)
    }
}