package com.app.miniproject.presentation.supplier.view

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
import com.app.miniproject.data.source.remote.response.SupplierResponse
import com.app.miniproject.databinding.FragmentSupplierBinding
import com.app.miniproject.domain.model.Supplier
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.supplier.adapter.SupplierAdapter
import com.app.miniproject.presentation.supplier.viewmodel.SupplierViewModel
import com.app.miniproject.utils.*
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
        setButtonClicked()
    }

    private fun setButtonClicked() {
        supplierAdapter.setOnButtonClickCallback(object : SupplierAdapter.OnButtonClickCallback {
            override fun onDeleteClicked(item: Supplier?) {
                customAlterDialog(item)
            }

            override fun onUpdateClicked(item: Supplier?) {}
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

    private fun callApiCreateSupplier(
        supplierName: String,
        phoneNumber: String,
        address: String,
        alertDialog: AlertDialog,
        progressBar: ProgressBar
    ) {
        val data = SupplierResponse(
            namaSupplier = supplierName,
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
                    viewModel.createSupplier.collect { uiState ->
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

    private fun customAlterDialog(item: Supplier?) {
        val title = resources.getString(R.string.title_alert_dialog_information)
        val subTitle =
            resources.getString(R.string.sub_title_alert_dialog_information, item?.namaSupplier)
        requireContext().showAlertDialogInformation(
            layoutInflater,
            title,
            subTitle
        ) { alertDialog, progressBar ->
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
                        viewModel.deleteSupplier.collect { uiState ->
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

    private fun customDialogCreate() {
        val builder = AlertDialog.Builder(requireContext())
        val customLayout =
            layoutInflater.inflate(R.layout.custom_alert_dialog_create_supplier, null)
        builder.setView(customLayout)
        val edtSupplierName = customLayout.findViewById<TextView>(R.id.edt_supplier_name)
        val edtPhoneNumber = customLayout.findViewById<TextView>(R.id.edt_phone_number)
        val edtAddress = customLayout.findViewById<TextView>(R.id.edt_address)
        val progressBar = customLayout.findViewById<ProgressBar>(R.id.progress_bar)
        val btnCreate = customLayout.findViewById<AppCompatButton>(R.id.btn_create)
        val btnCancel = customLayout.findViewById<AppCompatButton>(R.id.btn_cancel)
        val dialog = builder.create()

        btnCreate.setOnClickListener {
            callApiCreateSupplier(
                edtSupplierName.text.toString(),
                edtPhoneNumber.text.toString(),
                edtAddress.text.toString(),
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