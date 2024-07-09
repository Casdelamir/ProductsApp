package com.example.productsapp.data.entity

import com.google.gson.annotations.SerializedName

class ProductsByCategory(
    @SerializedName("products") val products: List<Product>,
    @SerializedName("total") val quantity: Int
) {
}