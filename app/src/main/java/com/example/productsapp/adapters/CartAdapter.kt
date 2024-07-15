package com.example.productsapp.adapters

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.R
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.data.sqlite.Cart
import com.example.productsapp.databinding.CartRecycleViewBinding
import kotlin.math.round

class CartAdapter (private var dataSet: List<Cart> = emptyList()) : RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartRecycleViewBinding.inflate(LayoutInflater.from(parent.context))
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        // Here we pass the lambda function onClickListener to the render function and execute it there
        holder.render(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(dataSet: List<Cart>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class CartViewHolder(private val binding: CartRecycleViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun render(cart: Cart) {
            ApiServicesImplementation.getProductById(cart.product) {
                binding.productCartName.text = itemView.context.getString(R.string.product_name, it.name)
            }
            binding.quantityProduct.text = itemView.context.getString(R.string.quantity, cart.quantity.toString())

            binding.totalPriceProduct.text = itemView.context.getString(R.string.total_price, String.format("%.2f", cart.totalPrice))
        }
    }
}