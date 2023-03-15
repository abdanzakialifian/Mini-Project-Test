package com.app.miniproject.presentation.home.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.app.miniproject.databinding.FragmentHomeBinding
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.home.adapter.HomeAdapter
import com.app.miniproject.presentation.home.viewmodel.HomeViewModel
import com.app.miniproject.utils.UiState
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
                            when (loadState.refresh) {
                                is LoadState.Loading -> Toast.makeText(
                                    requireContext(), "Loading", Toast.LENGTH_SHORT
                                ).show()
                                is LoadState.NotLoading -> {}
                                is LoadState.Error -> Toast.makeText(
                                    requireContext(), "ERROR", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}