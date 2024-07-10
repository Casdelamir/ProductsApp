package com.example.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productsapp.R
import com.example.productsapp.adapters.ProductAdapter
import com.example.productsapp.data.entity.Product
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.databinding.ActivityProductsBinding
import com.example.productsapp.utils.SessionManager

class ProductsActivityRecycleView : AppCompatActivity() {

    lateinit var binding: ActivityProductsBinding
    lateinit var adapter: ProductAdapter
    lateinit var categoryId: String
    lateinit var listProducts: List<Product>
    var favoriteProduct: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        categoryId = intent.getStringExtra("categoryId")!!
        supportActionBar?.title = categoryId.replaceFirstChar { c -> c.uppercase() }

        adapter = ProductAdapter() {
            navigateToProduct(it.id)
        }

        favoriteProduct = SessionManager(this).getFavoriteProduct()

        binding.productsRecycleView.adapter = adapter
        binding.productsRecycleView.layoutManager = GridLayoutManager(this, 2)

        ApiServicesImplementation.getProductsByCategory(categoryId) {
            listProducts = it.products
            adapter.updateData(it.products)
        }
    }

    fun navigateToProduct(productId: Int) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("productId", productId)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_products, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            //This is the arrow button in the action bar
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_favorite -> {
                listProducts = listProducts.filter { p -> p.id == favoriteProduct }
                if (listProducts.isEmpty()) {
                    Toast.makeText(this, "There is no favorite product in this category", Toast.LENGTH_SHORT).show()
                }else {
                adapter.updateData(listProducts)
                }
                Log.i("MENU", "Favorite is selected")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        ApiServicesImplementation.getProductsByCategory(categoryId) {
            listProducts = it.products
            favoriteProduct = SessionManager(this).getFavoriteProduct()
            adapter.updateData(it.products)
        }
    }
}