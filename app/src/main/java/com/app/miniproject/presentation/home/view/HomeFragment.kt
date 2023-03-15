package com.app.miniproject.presentation.home.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.app.miniproject.R
import com.app.miniproject.databinding.FragmentHomeBinding
import com.app.miniproject.domain.model.DataItem
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.home.adapter.HomeAdapter
import com.app.miniproject.presentation.home.viewmodel.HomeViewModel
import com.app.miniproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseVBFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callApi()
        setButtonClicked()
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
                    viewModel.getItemsList.collect { pagingData ->
                        homeAdapter.submitData(pagingData)
                        binding.rvItem.adapter = homeAdapter
                        homeAdapter.addLoadStateListener { loadState ->
                            when (val state = loadState.refresh) {
                                is LoadState.Loading -> {
                                    binding.apply {
                                        shimmerAnimation.visible()
                                        shimmerAnimation.startShimmer()
                                        rvItem.gone()
                                    }
                                }
                                is LoadState.NotLoading -> {
                                    binding.apply {
                                        shimmerAnimation.gone()
                                        shimmerAnimation.stopShimmer()
                                        rvItem.visible()
                                    }
                                }
                                is LoadState.Error -> {
                                    binding.apply {
                                        shimmerAnimation.gone()
                                        shimmerAnimation.stopShimmer()
                                        rvItem.gone()
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

    private fun setButtonClicked() {
        homeAdapter.setOnButtonClickCallback(object : HomeAdapter.OnButtonClickCallback {
            override fun onDeleteClicked(item: DataItem?) {
                customAlterDialog(item)
            }

            override fun onUpdateClicked(item: DataItem?) {}
        })
    }

    private fun customAlterDialog(item: DataItem?) {
        val title = resources.getString(R.string.title_alert_dialog_information)
        val subTitle =
            resources.getString(R.string.sub_title_alert_dialog_information, item?.namaBarang)
        requireContext().showAlertDialogInformation(layoutInflater, title, subTitle) { alertDialog, progressBar ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getUserToken.collect { token ->
                            viewModel.setToken("Bearer $token")
                        }
                    }
                    launch {
                        viewModel.setId(item?.id ?: 0)
                    }
                    launch {
                        viewModel.deleteItem.collect { uiState ->
                            when (uiState) {
                                is UiState.Loading -> progressBar.visible()
                                is UiState.Success -> {
                                    alertDialog.dismiss()
                                    progressBar.gone()
                                    callApi()
                                }
                                is UiState.Error -> {
                                    progressBar.gone()
                                    view?.showSnackBar(
                                        layoutInflater,
                                        uiState.message
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