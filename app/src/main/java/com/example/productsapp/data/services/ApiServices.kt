package com.example.productsapp.data.services

import com.example.productsapp.data.entity.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
        //@GET("search/{name}")
        //suspend fun findSuperHeroesByName(@Path("name") query: String) : SuperHeroListResponse

        //@GET("{id}")
        //suspend fun findSuperHeroById(@Path("id") query: String) : SuperHero

    @GET("categories")
    suspend fun getAllProductsCategories() : Response<List<Category>>
}