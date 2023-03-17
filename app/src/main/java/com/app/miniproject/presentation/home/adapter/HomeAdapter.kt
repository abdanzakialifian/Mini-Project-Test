package com.app.miniproject.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.miniproject.databinding.ItemListBinding
import com.app.miniproject.domain.model.DataItem
import com.app.miniproject.utils.collapse
import com.app.miniproject.utils.expand
import com.app.miniproject.utils.formatRupiah
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    PagingDataAdapter<DataItem, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    private var isExpanded = false

    private lateinit var onButtonClickCallback: OnButtonClickCallback

    fun setOnButtonClickCallback(onButtonClickCallback: OnButtonClickCallback) {
        this.onButtonClickCallback = onButtonClickCallback
    }

    inner class HomeViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem?) {
            binding.apply {
                tvTitleItem.text = item?.namaBarang
                tvPrice.text = item?.harga?.formatRupiah()
                tvStock.text = item?.stok.toString()
                tvSupplierName.text = item?.supplier?.namaSupplier
                tvPhoneNumber.text = item?.supplier?.noTelp
                tvAddress.text = item?.supplier?.alamat
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
                btnDelete.setOnClickListener {
                    onButtonClickCallback.onDeleteClicked(item)
                }
                btnUpdate.setOnClickListener {
                    onButtonClickCallback.onUpdateClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).run {
            HomeViewHolder(this)
        }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = position

    interface OnButtonClickCallback {
        fun onDeleteClicked(item: DataItem?)
        fun onUpdateClicked(item: DataItem?)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
                oldItem == newItem
        }
    }
}