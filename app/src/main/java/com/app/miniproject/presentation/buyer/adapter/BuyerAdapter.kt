package com.app.miniproject.presentation.buyer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.miniproject.databinding.ItemListBuyerBinding
import com.app.miniproject.domain.model.DataBuyer
import com.app.miniproject.utils.collapse
import com.app.miniproject.utils.expand
import javax.inject.Inject

class BuyerAdapter @Inject constructor() :
    PagingDataAdapter<DataBuyer, BuyerAdapter.BuyerViewHolder>(DIFF_CALLBACK) {

    private var isExpanded = false

    private lateinit var onButtonClickCallback: OnButtonClickCallback

    fun setOnButtonClickCallback(onButtonClickCallback: OnButtonClickCallback) {
        this.onButtonClickCallback = onButtonClickCallback
    }

    inner class BuyerViewHolder(private val binding: ItemListBuyerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataBuyer?) {
            binding.apply {
                tvTitleBuyer.text = item?.namaPembeli
                tvGender.text = item?.jenisKelamin
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
                btnDelete.setOnClickListener {
                    onButtonClickCallback.onDeleteClicked(item)
                }
                btnUpdate.setOnClickListener {
                    onButtonClickCallback.onUpdateClicked(item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BuyerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyerViewHolder =
        ItemListBuyerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).run {
            BuyerViewHolder(this)
        }

    override fun getItemViewType(position: Int): Int = position

    interface OnButtonClickCallback {
        fun onDeleteClicked(item: DataBuyer?)
        fun onUpdateClicked(item: DataBuyer?)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataBuyer>() {
            override fun areItemsTheSame(oldItem: DataBuyer, newItem: DataBuyer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataBuyer, newItem: DataBuyer): Boolean =
                oldItem == newItem
        }
    }
}