package com.app.miniproject.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.miniproject.databinding.ItemListDropDownBinding
import com.app.miniproject.domain.model.Supplier
import javax.inject.Inject

class SupplierDropDownAdapter @Inject constructor() :
    PagingDataAdapter<Supplier, SupplierDropDownAdapter.SupplierDropDownViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class SupplierDropDownViewHolder(private val binding: ItemListDropDownBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Supplier?) {
            binding.apply {
                tvSupplier.text = item?.namaSupplier
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierDropDownViewHolder =
        ItemListDropDownBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).run {
            SupplierDropDownViewHolder(this)
        }

    override fun onBindViewHolder(holder: SupplierDropDownViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = position

    interface OnItemClickCallback {
        fun onItemClicked(item: Supplier?)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Supplier>() {
            override fun areItemsTheSame(oldItem: Supplier, newItem: Supplier): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Supplier, newItem: Supplier): Boolean =
                oldItem == newItem
        }
    }
}