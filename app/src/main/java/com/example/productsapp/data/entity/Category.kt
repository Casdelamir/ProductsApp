package com.example.productsapp.data.entity

import com.google.gson.annotations.SerializedName

class Category(
    @SerializedName("slug") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {
}