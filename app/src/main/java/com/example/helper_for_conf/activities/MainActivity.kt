package com.example.helper_for_conf.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.helper_for_conf.databinding.ActivityMainBinding
import com.example.helper_for_conf.activities.calculators.DiameterCalculatorActivity
import com.example.helper_for_conf.activities.calculators.RatioCalculatorActivity
import com.example.helper_for_conf.activities.recipes.RecipeListActivity

class MainActivity : AppCompatActivity() {

    // Инициализация View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Обработка нажатия на кнопку "Калькулятор диаметра"
        binding.btnDiameterCalculator.setOnClickListener {
            startActivity(Intent(this, DiameterCalculatorActivity::class.java))
        }

        // Обработка нажатия на кнопку "Калькулятор соотношения"
        binding.btnRatioCalculator.setOnClickListener {
            startActivity(Intent(this, RatioCalculatorActivity::class.java))
        }

        // Обработка нажатия на кнопку "Рецепты"
        binding.btnRecipes.setOnClickListener {
            startActivity(Intent(this, RecipeListActivity::class.java))
        }
    }
}