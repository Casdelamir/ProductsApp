package com.example.productsapp.data.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.productsapp.data.sqlite.Cart.Companion.COLUMN_NAME_PRICE
import com.example.productsapp.data.sqlite.Cart.Companion.COLUMN_NAME_PRODUCT
import com.example.tasklist.utils.DatabaseManager


class CartDAO(val context: Context) {

    private val databaseManager: DatabaseManager = DatabaseManager(context)
     private val projection = arrayOf(BaseColumns._ID, Cart.COLUMN_NAME_PRODUCT, Cart.COLUMN_NAME_QUANTITY, Cart.COLUMN_NAME_PRICE)

    fun insert(cart: Cart) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Cart.COLUMN_NAME_PRODUCT, cart.product)
        values.put(Cart.COLUMN_NAME_QUANTITY, cart.quantity)
        values.put(Cart.COLUMN_NAME_PRICE, cart.totalPrice)

        val newRowId = db.insert(Cart.TABLE_NAME, null, values)
        cart.id = newRowId.toInt()
    }

    fun update(cart: Cart) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Cart.COLUMN_NAME_PRODUCT, cart.product)
        values.put(Cart.COLUMN_NAME_QUANTITY, cart.quantity)
        values.put(Cart.COLUMN_NAME_PRICE, cart.totalPrice)

        val updatedRows = db.update(
            Cart.TABLE_NAME,
            values,
            "${BaseColumns._ID} = ${cart.id}",
            null
        )
    }

    fun delete(cart: Cart) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Cart.TABLE_NAME, "${BaseColumns._ID} = ${cart.id}", null)
    }

    @SuppressLint("Range")
    fun find(id: Int): Cart? {
        val db = databaseManager.readableDatabase

        val cursor = db.query(
            Cart.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            "$COLUMN_NAME_PRODUCT = $id",     // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            null   // The sort order
        )

        var cart: Cart? = null
        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val productId = cursor.getInt(cursor.getColumnIndexOrThrow(Cart.COLUMN_NAME_PRODUCT))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(Cart.COLUMN_NAME_QUANTITY))
            val price = cursor.getDouble(cursor.getColumnIndex(Cart.COLUMN_NAME_PRICE))

            cart = Cart(id, productId, quantity, price)
        }
        cursor.close()
        db.close()
        return cart
    }

    @SuppressLint("Range")
    fun findAll(): List<Cart> {
        val db = databaseManager.readableDatabase

        val cursor = db.query(
            Cart.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            null,                            // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            "$COLUMN_NAME_PRICE ASC"         // The sort order
        )

        var carts = mutableListOf<Cart>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val productId = cursor.getInt(cursor.getColumnIndexOrThrow(Cart.COLUMN_NAME_PRODUCT))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(Cart.COLUMN_NAME_QUANTITY))
            val price = cursor.getDouble(cursor.getColumnIndex(Cart.COLUMN_NAME_PRICE))

            val cart = Cart(id, productId, quantity, price)
            carts.add(cart)
        }
        cursor.close()
        db.close()
        return carts
    }
}