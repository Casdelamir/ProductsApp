package com.example.productsapp.data.sqlite


import android.provider.BaseColumns

data class Cart(var id: Int, var productId: Int, var quantity: Int, var totalPrice: Double) {

    companion object {
        const val TABLE_NAME = "Cart"
        const val COLUMN_NAME_PRODUCT = "product"
        const val COLUMN_NAME_QUANTITY = "quantity"
        const val COLUMN_NAME_PRICE = "price"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME_PRODUCT INTEGER," +
                    "$COLUMN_NAME_QUANTITY INTEGER," +
                    "$COLUMN_NAME_PRICE REAL)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}