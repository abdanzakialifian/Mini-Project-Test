package com.app.miniproject.presentation.supplier.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.app.miniproject.databinding.FragmentSupplierBinding
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.supplier.adapter.SupplierAdapter
import com.app.miniproject.presentation.supplier.viewmodel.SupplierViewModel
import com.app.miniproject.utils.gone
import com.app.miniproject.utils.showSnackBar
import com.app.miniproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SupplierFragment : BaseVBFragment<FragmentSupplierBinding>() {

    @Inject
    lateinit var supplierAdapter: SupplierAdapter

    private val viewModel by viewModels<SupplierViewModel>()

    override fun getViewBinding(): FragmentSupplierBinding =
        FragmentSupplierBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callApi()
    }

    private fun callApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getUserToken.collect { token ->
                        viewModel.setToken("Bearer $token")
                    }
                }
                launch {
                    viewModel.getSupplierList.collect { pagingData ->
                        supplierAdapter.submitData(pagingData)
                        binding.rvSupplier.adapter = supplierAdapter

                        supplierAdapter.addLoadStateListener { loadState ->
                            when (val state = loadState.refresh) {
                                is LoadState.Loading -> {
                                    binding.apply {
                                        shimmerAnimation.visible()
                                        shimmerAnimation.startShimmer()
                                        rvSupplier.gone()
                                    }
                                }
                                is LoadState.NotLoading -> {
                                    binding.apply {
                                        shimmerAnimation.gone()
                                        shimmerAnimation.stopShimmer()
                                        rvSupplier.visible()
                                    }
                                }
                                is LoadState.Error -> {
                                    binding.apply {
                                        shimmerAnimation.gone()
                                        shimmerAnimation.stopShimmer()
                                        rvSupplier.gone()
                                    }
                                    view?.showSnackBar(
                                        layoutInflater, state.error.message.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}