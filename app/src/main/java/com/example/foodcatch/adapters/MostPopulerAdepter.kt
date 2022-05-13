package com.example.foodcatch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodcatch.databinding.PopulerItemsBinding
import com.example.foodcatch.pojo.MealsByCategory

class MostPopulerAdepter():RecyclerView.Adapter<MostPopulerAdepter.PupulerMealViewHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory)->Unit)

    fun setMeals(listMealsBy : ArrayList<MealsByCategory>){
        this.mealsList = listMealsBy
        notifyDataSetChanged()
    }

    class PupulerMealViewHolder( val binding: PopulerItemsBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PupulerMealViewHolder {
        return PupulerMealViewHolder(PopulerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: PupulerMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopulerMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
    }
    override fun getItemCount(): Int {
        return mealsList.size
    }
}