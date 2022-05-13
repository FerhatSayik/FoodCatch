package com.example.foodcatch.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodcatch.activities.CategoryMealsActivity
import com.example.foodcatch.activities.MealActivity
import com.example.foodcatch.adapters.CategoriesAdapter
import com.example.foodcatch.adapters.MostPopulerAdepter
import com.example.foodcatch.databinding.FragmentHomeBinding
import com.example.foodcatch.pojo.Category
import com.example.foodcatch.pojo.MealsByCategory
import com.example.foodcatch.pojo.Meal
import com.example.foodcatch.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopulerAdepter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID ="com.example.foodcatch.fragments.idMeal"
        const val MEAL_NAME ="com.example.foodcatch.fragments.nameMeal"
        const val MEAL_THUMB ="com.example.foodcatch.fragments.thumbMeal"
        const val CATEGORY_NAME ="com.example.foodcatch.fragments.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm =  ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopulerAdepter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopulerItemsRecyclerView()
        prepareCategoryItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopulerItemsLiveData()
        onPopulerItemClick()

        homeMvvm.getCategories()
        observeCategoriesLiveDate()
        onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        categoriesAdapter.onItemClick = {category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryItemsRecyclerView() {
        binding.recViewCategories.apply { layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        adapter = categoriesAdapter}
    }

    private fun observeCategoriesLiveDate() {
        homeMvvm.observerCategorisLiveData().observe(viewLifecycleOwner, Observer { categories ->
                categoriesAdapter.setCategoryList(categories)

        })
    }

    private fun onPopulerItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopulerItemsRecyclerView() {
        binding.recViewMealsPopular.apply { layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        adapter = popularItemsAdapter
        }
    }

    private fun observePopulerItemsLiveData() {
        homeMvvm.observePopulerItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(listMealsBy = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal


        }
    }

}