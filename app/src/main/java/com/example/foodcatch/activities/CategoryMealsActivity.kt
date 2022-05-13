package com.example.foodcatch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodcatch.R
import com.example.foodcatch.adapters.CategoryMealsAdapter
import com.example.foodcatch.databinding.ActivityCategoryMealsBinding
import com.example.foodcatch.fragments.HomeFragment
import com.example.foodcatch.pojo.MealsByCategory
import com.example.foodcatch.pojo.MealsByCategoryList
import com.example.foodcatch.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryAdapter:CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryAdapter = CategoryMealsAdapter()
        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealList->
            binding.tvCategoryCount.text = "Count:${mealList.size}"
            categoryAdapter.setMeals(mealList)
        })

    }

    private fun prepareRecyclerView() {
        binding.rvMeals.apply { layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        adapter = categoryAdapter}
    }
}