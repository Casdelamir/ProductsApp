package com.example.productsapp.data.services

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.productsapp.data.entity.Category
import com.example.productsapp.data.entity.Product
import com.example.productsapp.data.entity.ProductsByCategory
import com.example.productsapp.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApiServicesImplementation {
    companion object {
        fun getAllProductsCategories(implementLayout: (List<Category>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiService = RetrofitProvider.getRetrofit().create(ApiServices::class.java)
                    val response = apiService.getAllProductsCategories()
                    Log.i("HTTP", "${response.code()}")
                    Handler(Looper.getMainLooper()).post {
                        //Code that runs in main
                        if (response.isSuccessful) {
                            val categories = response.body()
                            categories?.forEach {
                                Log.d("MainActivity", "Category: ${it.name}")
                            }
                        } else {
                            Log.e("MainActivity", "Request failed with code: ${response.code()}")
                        }
                        implementLayout(response.body()!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun getProductsByCategory(query: String, implementLayout: (ProductsByCategory) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiService = RetrofitProvider.getRetrofit().create(ApiServices::class.java)
                    val response = apiService.getProductsByCategory(query)
                    Log.i("HTTP", "${response.code()}")
                    Handler(Looper.getMainLooper()).post {
                        //Code that runs in main
                        if (response.isSuccessful) {
                            val products = response.body()?.products
                            products?.forEach {
                                Log.d("MainActivity", "Category: ${it.name}")
                            }
                        } else {
                            Log.e("MainActivity", "Request failed with code: ${response.code()}")
                        }
                        implementLayout(response.body()!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun getProductById(query: Int, implementLayout: (Product) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiService = RetrofitProvider.getRetrofit().create(ApiServices::class.java)
                    val response = apiService.getProductById(query)
                    Log.i("HTTP", "${response.code()}")
                    Handler(Looper.getMainLooper()).post {
                        //Code that runs in main
                        val product = response.body()
                        if (response.isSuccessful) {
                            if (product != null) {
                                product.url = response.raw().request().url().toString()
                                Log.d("MainActivity", "Category: ${product.name} -> ${response.raw().request().url()}")
                            }
                        } else {
                            Log.e("MainActivity", "Request failed with code: ${response.code()}")
                        }
                        implementLayout(product!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}