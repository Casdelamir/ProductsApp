package com.example.productsapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    companion object{
        const val FAVORITE_PRODUCT: String = "FAVORITE_PRODUCT"
        const val FAVORITE_CATEGORY: String = "FAVORITE_CATEGORY"
    }

    //val sharedPref: SharedPreferences = context.getSharedPreferences("horoscope_session", Context.MODE_PRIVATE)

    // use init to initialize the Shared references optional:

    val sharedPref: SharedPreferences
    init {
        sharedPref = context.getSharedPreferences("APP_SESSION", Context.MODE_PRIVATE)
    }

    fun setProductToFavorite(id: Int?) {
        if (id != null) {
            sharedPref.edit().putInt(FAVORITE_PRODUCT, id).apply()
        }
    }

    fun setProductFavoriteToNull() {
        sharedPref.edit().putInt(FAVORITE_PRODUCT, -1).apply()
    }

    fun getFavoriteProduct() : Int {
        return sharedPref.getInt(FAVORITE_PRODUCT, -1)
    }

    fun isProductFavorite(id: Int) : Boolean {
        return sharedPref.getInt(FAVORITE_PRODUCT, -1) == id
    }
}