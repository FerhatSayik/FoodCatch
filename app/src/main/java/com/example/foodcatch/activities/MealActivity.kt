package com.example.foodcatch.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodcatch.R
import com.example.foodcatch.databinding.ActivityMealBinding
import com.example.foodcatch.fragments.HomeFragment
import com.example.foodcatch.pojo.Meal
import com.example.foodcatch.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInView()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailLiveData() {
        mealMvvm.observerMealDetailLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal=t

                binding.tvCategory.text="Category:${meal!!.strCategory}"
                binding.tvArea.text="Area:${meal!!.strArea}"
                binding.tvInstructionsStep.text=meal!!.strInstructions

                youtubeLink=meal.strYoutube
            }

        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collesingToolbar.title = mealName
        binding.collesingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collesingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val Intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

}