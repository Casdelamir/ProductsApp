package com.example.productsapp.data.entity

import com.google.gson.annotations.SerializedName

class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("brand") val brand: String
) {
}