package com.example.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productsapp.adapters.CategoryProductsAdapter
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CategoryProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CategoryProductsAdapter() {
            navigateSelectedCategoryProducts(it.id)
        }

        binding.categoriesRecycleView.adapter = adapter
        binding.categoriesRecycleView.layoutManager = GridLayoutManager(this, 2)

        supportActionBar?.title = "Categories of products"

        ApiServicesImplementation.getAllProductsCategories { list ->
            adapter.updateData(list)
        }
    }

    fun navigateSelectedCategoryProducts(categoryId: String) {
        val intent = Intent(this, ProductsActivityRecycleView::class.java)
        intent.putExtra("categoryId", categoryId)
        startActivity(intent)
    }
}