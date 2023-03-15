package com.app.miniproject.presentation.supplier.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.miniproject.databinding.ItemListSupplierBinding
import com.app.miniproject.domain.model.Supplier
import com.app.miniproject.utils.collapse
import com.app.miniproject.utils.expand
import javax.inject.Inject

class SupplierAdapter @Inject constructor() :
    PagingDataAdapter<Supplier, SupplierAdapter.SupplierViewHolder>(DIFF_CALLBACK) {

    private var isExpanded = false

    inner class SupplierViewHolder(private val binding: ItemListSupplierBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Supplier?) {
            binding.apply {
                tvTitleSupplier.text = item?.namaSupplier
                tvPhoneNumber.text = item?.noTelp
                tvAddress.text = item?.alamat

                layoutMoreInfo.setOnClickListener {
                    if (!isExpanded) {
                        isExpanded = true
                        imgArrow.animate().rotation(180F).duration = 300L
                        layoutMoreInfoContent.expand()
                    } else {
                        isExpanded = false
                        imgArrow.animate().rotation(0F).duration = 300L
                        layoutMoreInfoContent.collapse()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder =
        ItemListSupplierBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).run {
            SupplierViewHolder(this)
        }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        holder.bind(getItem(position))
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