package com.app.miniproject.presentation.buyer.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.app.miniproject.R
import com.app.miniproject.data.source.remote.response.DataBuyerResponse
import com.app.miniproject.databinding.FragmentBuyerBinding
import com.app.miniproject.domain.model.DataBuyer
import com.app.miniproject.presentation.adapter.LoadingStateAdapter
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.buyer.adapter.BuyerAdapter
import com.app.miniproject.presentation.buyer.viewmodel.BuyerViewModel
import com.app.miniproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BuyerFragment : BaseVBFragment<FragmentBuyerBinding>() {

    @Inject
    lateinit var buyerAdapter: BuyerAdapter

    private val viewModel by viewModels<BuyerViewModel>()

    override fun getViewBinding(): FragmentBuyerBinding =
        FragmentBuyerBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callApi()
        setButtonClicked()
        binding.rvBuyer.adapter = buyerAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                buyerAdapter.retry()
            }
        )
    }

    private fun setButtonClicked() {
        buyerAdapter.setOnButtonClickCallback(object : BuyerAdapter.OnButtonClickCallback {
            override fun onDeleteClicked(item: DataBuyer?) {
                customAlterDialogInformation(item)
            }

            override fun onUpdateClicked(item: DataBuyer?) {}
        })

        binding.imgCreate.setOnClickListener {
            customDialogCreate()
        }
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
                    viewModel.getBuyerList.collect { pagingData ->
                        buyerAdapter.submitData(pagingData)
                        binding.rvBuyer.adapter = buyerAdapter
                        buyerAdapter.addLoadStateListener { loadState ->
                            when (val state = loadState.refresh) {
                                is LoadState.Loading -> {
                                    binding.apply {
                                        shimmerAnimation.visible()
                                        shimmerAnimation.startShimmer()
                                        rvBuyer.gone()
                                    }
                                }
                                is LoadState.NotLoading -> {
                                    binding.apply {
                                        shimmerAnimation.gone()
                                        shimmerAnimation.stopShimmer()
                                        rvBuyer.visible()
                                    }
                                }
                                is LoadState.Error -> {
                                    binding.apply {
                                        shimmerAnimation.gone()
                                        shimmerAnimation.stopShimmer()
                                        rvBuyer.gone()
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

    private fun callApiDeleteBuyer(
        idItem: Int,
        alertDialog: AlertDialog,
        progressBar: ProgressBar
    ) {
        viewModel.setId(idItem)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getUserToken.collect { token ->
                        viewModel.setToken("Bearer $token")
                    }
                }
                launch {
                    viewModel.deleteBuyer.collect { uiState ->
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
                                    layoutInflater, uiState.message
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun callApiCreateBuyer(
        buyerName: String,
        phoneNumber: String,
        address: String,
        gender: String,
        alertDialog: AlertDialog,
        progressBar: ProgressBar
    ) {
        val data = DataBuyerResponse(
            namaPembeli = buyerName,
            jenisKelamin = gender,
            id = 0,
            noTelp = phoneNumber,
            alamat = address
        )

        viewModel.setData(data)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getUserToken.collect { token ->
                        viewModel.setToken("Bearer $token")
                    }
                }
                launch {
                    viewModel.createBuyer.collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> progressBar.visible()
                            is UiState.Success -> {
                                alertDialog.dismiss()
                                callApi()
                            }
                            is UiState.Error -> {
                                progressBar.gone()
                                view?.showSnackBar(layoutInflater, uiState.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun customAlterDialogInformation(item: DataBuyer?) {
        val title = resources.getString(R.string.title_alert_dialog_information)
        val subTitle =
            resources.getString(R.string.sub_title_alert_dialog_information, item?.namaPembeli)
        requireContext().showAlertDialogInformation(
            layoutInflater, title, subTitle
        ) { alertDialog, progressBar ->
            callApiDeleteBuyer(item?.id ?: 0, alertDialog, progressBar)
        }
    }

    private fun customDialogCreate() {
        val builder = AlertDialog.Builder(requireContext())
        val customLayout = layoutInflater.inflate(R.layout.custom_alert_dialog_create_buyer, null)
        builder.setView(customLayout)
        val edtBuyerName = customLayout.findViewById<TextView>(R.id.edt_buyer_name)
        val edtPhoneNumber = customLayout.findViewById<TextView>(R.id.edt_phone_number)
        val edtAddress = customLayout.findViewById<TextView>(R.id.edt_address)
        val edtGender = customLayout.findViewById<TextView>(R.id.edt_gender)
        val progressBar = customLayout.findViewById<ProgressBar>(R.id.progress_bar)
        val btnCreate = customLayout.findViewById<AppCompatButton>(R.id.btn_create)
        val btnCancel = customLayout.findViewById<AppCompatButton>(R.id.btn_cancel)
        val dialog = builder.create()

        btnCreate.setOnClickListener {
            callApiCreateBuyer(
                edtBuyerName.text.toString(),
                edtPhoneNumber.text.toString(),
                edtAddress.text.toString(),
                edtGender.text.toString(),
                dialog,
                progressBar
            )
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}