package com.example.productsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.data.entity.Category
import com.example.productsapp.data.entity.Product
import com.example.productsapp.databinding.CategoryRecycleViewBinding
import com.example.productsapp.databinding.ProductRecycleViewBinding

class ProductAdapter (private var dataSet: List<Product> = emptyList(), private val onClickListener: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductRecycleViewBinding.inflate(LayoutInflater.from(parent.context))
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // Here we pass the lambda function onClickListener to the render function and execute it there
        holder.render(dataSet[position], onClickListener)
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(dataSet: List<Product>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: ProductRecycleViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun render(product: Product, onClickListener: (Product) -> Unit) {
            binding.productName.text = product.name

            binding.cardProduct.setOnClickListener {
                onClickListener(product)
            }
        }
    }
}