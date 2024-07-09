package com.example.productsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.data.entity.Category
import com.example.productsapp.databinding.CategoryRecycleViewBinding

class CategoryProductsAdapter (private var dataSet: List<Category> = emptyList(), private val onClickListener: (Category) -> Unit) : RecyclerView.Adapter<CategoryProductsAdapter.CategoryViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryRecycleViewBinding.inflate(LayoutInflater.from(parent.context))
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Here we pass the lambda function onClickListener to the render function and execute it there
        holder.render(dataSet[position], onClickListener)
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(dataSet: List<Category>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class CategoryViewHolder(private val binding: CategoryRecycleViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun render(category: Category, onClickListener: (Category) -> Unit) {
            binding.categoryName.text = category.name

            binding.cardCategory.setOnClickListener {
                onClickListener(category)
            }
        }
    }
}