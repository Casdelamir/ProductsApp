package com.example.productsapp.data.services

import com.example.productsapp.data.entity.Category
import com.example.productsapp.data.entity.Product
import com.example.productsapp.data.entity.ProductsByCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("{id}")
    suspend fun getProductById(@Path("id") query: Int) : Response<Product>

    @GET("categories")
    suspend fun getAllProductsCategories() : Response<List<Category>>

    @GET("category/{id}")
    suspend fun getProductsByCategory(@Path("id") categoryId: String) : Response<ProductsByCategory>
}