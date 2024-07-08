package com.example.productsapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://dummyjson.com/products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}