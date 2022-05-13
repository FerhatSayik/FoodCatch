package com.example.foodcatch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodcatch.databinding.CategoryItemBinding
import com.example.foodcatch.pojo.Category
import com.example.foodcatch.pojo.MealsByCategory

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategeriesViewHolder>() {
    private var categoriesList = ArrayList<Category>()
    lateinit var onItemClick:((Category)->Unit)

    fun setCategoryList(categoriesListeBy : List<Category>){
        this.categoriesList = categoriesListeBy as ArrayList<Category>
        notifyDataSetChanged()
    }

    class CategeriesViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategeriesViewHolder {
        return CategeriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategeriesViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryItem.text = (categoriesList[position].strCategory)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}