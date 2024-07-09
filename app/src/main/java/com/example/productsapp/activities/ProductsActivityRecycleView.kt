package com.example.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productsapp.adapters.ProductAdapter
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.databinding.ActivityProductsBinding

class ProductsActivityRecycleView : AppCompatActivity() {

    lateinit var binding: ActivityProductsBinding
    lateinit var adapter: ProductAdapter
    lateinit var categoryId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        categoryId = intent.getStringExtra("categoryId")!!
        supportActionBar?.title = categoryId.replaceFirstChar { c -> c.uppercase() }

        adapter = ProductAdapter() {
            navigateToProduct(it.id)
        }

        binding.productsRecycleView.adapter = adapter
        binding.productsRecycleView.layoutManager = GridLayoutManager(this, 2)

        ApiServicesImplementation.getProductsByCategory(categoryId) {
            adapter.updateData(it.products)
        }
    }

    fun navigateToProduct(productId: Int) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("productId", productId)
        startActivity(intent)
    }
}