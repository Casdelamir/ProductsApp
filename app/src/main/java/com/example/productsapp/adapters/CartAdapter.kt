package com.example.productsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.R
import com.example.productsapp.data.services.ApiServicesImplementation
import com.example.productsapp.data.sqlite.Cart
import com.example.productsapp.databinding.CartRecycleViewBinding

class CartAdapter (private var dataSet: List<Cart> = emptyList(), private val onSaveClickListener: (Cart) -> Unit, private val onDeleteClickListener: (Cart) -> Unit) : RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartRecycleViewBinding.inflate(LayoutInflater.from(parent.context))
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        // Here we pass the lambda function onClickListener to the render function and execute it there
        holder.render(dataSet[position],onSaveClickListener , onDeleteClickListener)
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(dataSet: List<Cart>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class CartViewHolder(private val binding: CartRecycleViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun render(cart: Cart, onSaveClickListener: (Cart) -> Unit, onDeleteClickListener: (Cart) -> Unit) {
            //This is used for showing the key board and hide it
            val imm = ContextCompat.getSystemService(itemView.context, InputMethodManager::class.java)

            ApiServicesImplementation.getProductById(cart.productId) {
                binding.productCartName.text = itemView.context.getString(R.string.product_name, it.name)
            }
            binding.quantityProductValue.text = itemView.context.getString(R.string.quantity_value, cart.quantity.toString())
            binding.editQuantityValue.visibility = View.GONE
            binding.saveButton.visibility = View.GONE

            binding.totalPriceProduct.text = itemView.context.getString(R.string.total_price, String.format("%.2f", cart.totalPrice))

            binding.editButton.setOnClickListener() {
                binding.quantityProductValue.visibility = View.GONE
                binding.editQuantityValue.visibility = View.VISIBLE

                binding.editQuantityValue.setText(itemView.context.getString(R.string.quantity_value, cart.quantity.toString()))

                if(binding.editQuantityValue.requestFocus()) {
                    imm?.showSoftInput(binding.editQuantityValue, InputMethodManager.SHOW_IMPLICIT)
                }
            }

            binding.editQuantityValue.setOnEditorActionListener() {_, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.quantityProductValue.visibility = View.VISIBLE
                    binding.editQuantityValue.visibility = View.GONE
                    binding.saveButton.visibility = View.VISIBLE

                    val newQuantity: Int = binding.editQuantityValue.text.toString().toInt()
                    binding.quantityProductValue.text = newQuantity.toString()

                    val singleProductPrice = cart.totalPrice / cart.quantity
                    cart.totalPrice = singleProductPrice * newQuantity
                    cart.quantity = newQuantity

                    binding.totalPriceProduct.text = itemView.context.getString(R.string.total_price, String.format("%.2f", cart.totalPrice))
                    imm?.hideSoftInputFromWindow(binding.editQuantityValue.windowToken, 0)
                    true
                } else {
                    false
                }
            }

            binding.deleteButton.setOnClickListener() {
                onDeleteClickListener(cart)
            }

            binding.saveButton.setOnClickListener() {
                onSaveClickListener(cart)
            }
        }
    }
}