package com.example.productsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productsapp.R
import com.example.productsapp.adapters.CartAdapter
import com.example.productsapp.adapters.CategoryProductsAdapter
import com.example.productsapp.data.sqlite.CartDAO
import com.example.productsapp.databinding.ActivityCartBinding
import com.example.productsapp.databinding.ActivityMainBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartDAO = CartDAO(this)

        adapter = CartAdapter(cartDAO.findAll())

        binding.cartRecycleView.adapter = adapter
        binding.cartRecycleView.layoutManager = GridLayoutManager(this, 1)

        supportActionBar?.title = "Cart"
    }
}