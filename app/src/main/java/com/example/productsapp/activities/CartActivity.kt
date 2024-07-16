package com.example.productsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productsapp.R
import com.example.productsapp.adapters.CartAdapter
import com.example.productsapp.adapters.CategoryProductsAdapter
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.data.sqlite.CartDAO
import com.example.productsapp.databinding.ActivityCartBinding
import com.example.productsapp.databinding.ActivityMainBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: CartAdapter
    val cartDAO = CartDAO(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CartAdapter(cartDAO.findAll(),
            {
                cartDAO.update(it)
                Toast.makeText(this, "Product is updated", Toast.LENGTH_SHORT).show()
                adapter.updateData(cartDAO.findAll())
                updateCartPrice()
            },
            {cartDAO.delete(it)
            Toast.makeText(this, "Product is deleted", Toast.LENGTH_SHORT).show()
            adapter.updateData(cartDAO.findAll())
                updateCartPrice()
            }
        )

        binding.cartRecycleView.adapter = adapter
        binding.cartRecycleView.layoutManager = GridLayoutManager(this, 2)

        supportActionBar?.title = "Cart"
        updateCartPrice()

        //TO DO implement Order products button event
    }

    private fun updateCartPrice() {
        var cartPrice: Double = 0.0
        cartDAO.findAll().forEach { c -> cartPrice += c.totalPrice }
        binding.cartPrice.text = getString(R.string.price_cart, String.format("%.2f", cartPrice))
    }
}