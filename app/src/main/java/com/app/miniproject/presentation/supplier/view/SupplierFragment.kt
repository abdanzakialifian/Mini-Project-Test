package com.app.miniproject.presentation.supplier.view

import com.app.miniproject.databinding.FragmentSupplierBinding
import com.app.miniproject.presentation.base.BaseVBFragment

class SupplierFragment : BaseVBFragment<FragmentSupplierBinding>() {
    override fun getViewBinding(): FragmentSupplierBinding =
        FragmentSupplierBinding.inflate(layoutInflater)
}