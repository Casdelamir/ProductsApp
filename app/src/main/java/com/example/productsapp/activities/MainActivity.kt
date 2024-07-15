package com.example.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productsapp.R
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_categories, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            //This is the arrow button in the action bar
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_cart -> {
                goToCart()
                Log.i("MENU", "Favorite is selected")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun goToCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
}