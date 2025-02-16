package com.example.helper_for_conf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helper_for_conf.R
import com.example.helper_for_conf.models.Ingredient

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var ingredients: MutableList<Ingredient> = mutableListOf()

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteIngredient)

        fun bind(ingredient: Ingredient) {
            btnDelete.setOnClickListener {
                ingredients.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int = ingredients.size

    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    fun getIngredients(): List<Ingredient> = ingredients

    fun updateIngredients(newIngredients: List<Ingredient>) {
        ingredients.clear()
        ingredients.addAll(newIngredients)
        notifyDataSetChanged()
    }
}
