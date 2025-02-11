package com.example.helper_for_conf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helper_for_conf.R
import com.example.helper_for_conf.models.Ingredient

class IngredientAdapter(
    private var ingredients: List<Ingredient>, // Список ингредиентов
    private val onItemClick: (Ingredient) -> Unit // Лямбда для обработки клика
) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    // ViewHolder для ингредиентов
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientName: TextView = itemView.findViewById(R.id.ingredientName)
        private val ingredientQuantity: TextView = itemView.findViewById(R.id.ingredientQuantity)

        fun bind(ingredient: Ingredient) {
            ingredientName.text = ingredient.name
            ingredientQuantity.text = "${ingredient.quantity} ${ingredient.unit}"
            itemView.setOnClickListener {
                onItemClick(ingredient)
            }
        }
    }

    // Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    // Количество элементов в списке
    override fun getItemCount(): Int {
        return ingredients.size
    }

    // Метод для обновления списка ингредиентов
    fun updateIngredients(newIngredients: List<Ingredient>) {
        ingredients = newIngredients
        notifyDataSetChanged()
    }
}