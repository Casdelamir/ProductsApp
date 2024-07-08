package com.example.productsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.productsapp.R
import com.example.productsapp.adapters.CategoryProductsAdapter
import com.example.productsapp.data.services.ApiServices
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CategoryProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CategoryProductsAdapter() {}

        ApiServicesImplementation.getAllProductsCategories { list ->
            adapter.updateData(list)
        }
    }
}