package com.example.productsapp.data.services

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.productsapp.data.entity.Category
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
                        implementLayout(response.body()!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}