package com.example.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.productsapp.R
import com.example.productsapp.data.entity.Product
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.data.sqlite.Cart
import com.example.productsapp.data.sqlite.CartDAO
import com.example.productsapp.databinding.ActivityProductBinding
import com.example.productsapp.utils.SessionManager

class ProductActivity : AppCompatActivity() {

    var productId: Int = 0
    lateinit var product: Product
    lateinit var binding: ActivityProductBinding
    var favoriteProduct: Int = -1
    lateinit var sessionManger: SessionManager
    lateinit var itemFavoriteMenu: MenuItem
    lateinit var cartDAO: CartDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra("productId", -1)
        sessionManger = SessionManager(this)
        favoriteProduct = sessionManger.getFavoriteProduct()

        cartDAO = CartDAO(this)

        ApiServicesImplementation.getProductById(productId) {
            supportActionBar?.title = it.name
            product = it
            if (it.brand != null) {
                binding.productBrand.text = it.brand
            }else {
                binding.productBrand.text = it.name
            }
            binding.productDescription.text = it.description
            binding.productPrice.text = it.price.toString()
            }

        binding.addProductToCart.setOnClickListener(){
            val existingProductInTheCart: Cart? = cartDAO.find(productId)
            cartDAO.find(productId)
            val quantity: Int = binding.textInputQuantity.text.toString().toInt()
            val totalPrice = quantity * product.price
            val cart = Cart(-1, productId, quantity, totalPrice)
            cartDAO.insert(cart)
            Toast.makeText(this, "Product is added to card", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        itemFavoriteMenu = menu?.findItem(R.id.menu_favorite)!!
        //set icons: all icons are not favorite by default
        if (favoriteProduct == productId) {
            itemFavoriteMenu.setIcon(R.drawable.baseline_favorite_24)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            //This is the arrow button in the action bar
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_favorite -> {
                setFavorite()
                Log.i("MENU", "Favorite is selected")
                true
            }
            R.id.menu_share -> {
//              Implements share with other apps
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, product.url)
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                Log.i("MENU", "Share is selected")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (favoriteProduct != productId) {
            sessionManger.setProductToFavorite(productId)
            itemFavoriteMenu.setIcon(R.drawable.baseline_favorite_24)
        }else {
            sessionManger.setProductFavoriteToNull()
            itemFavoriteMenu.setIcon(R.drawable.baseline_favorite_border_24)
        }
    }
}