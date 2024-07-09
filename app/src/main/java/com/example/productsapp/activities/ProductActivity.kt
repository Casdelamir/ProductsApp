package com.example.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.productsapp.R
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    var productId: Int = 0
    lateinit var binding: ActivityProductBinding
    //lateinit var favoriteProduct: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra("productId", -1)
        //favoriteProduct = SessionManager(this).getFavoriteHoroscope().toString()

        ApiServicesImplementation.getProductById(productId) {
            supportActionBar?.title = it.name
            if (it.brand != "") {
                binding.productBrand.text = it.brand
            }else {
                binding.productBrand.text = it.name
            }
            binding.productDescription.text = it.description
            binding.productPrice.text = it.price.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        //itemFavoriteMenu = menu?.findItem(R.id.menu_favorite)
        //set icons: all icons are not favorite by default
        //if (favorite == id) {
            //itemFavoriteMenu?.setIcon(R.drawable.baseline_favorite_24)
        //}
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
                //setFavorite()
                Log.i("MENU", "Favorite is selected")
                true
            }
            R.id.menu_share -> {
//              Implements share with other apps
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                //sendIntent.putExtra(Intent.EXTRA_TEXT, dailyPredicton.text)
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                Log.i("MENU", "Share is selected")
                true
            }
            R.id.menu_save -> {
                Log.i("MENU", "Save is selected")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}