package com.app.miniproject.presentation.home.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.app.miniproject.R
import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.data.source.remote.response.SupplierResponse
import com.app.miniproject.databinding.FragmentHomeBinding
import com.app.miniproject.domain.model.DataItem
import com.app.miniproject.domain.model.Supplier
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.home.adapter.HomeAdapter
import com.app.miniproject.presentation.home.adapter.SupplierDropDownAdapter
import com.app.miniproject.presentation.home.viewmodel.HomeViewModel
import com.app.miniproject.utils.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseVBFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var homeAdapter: HomeAdapter

    @Inject
    lateinit var supplierDropDownAdapter: SupplierDropDownAdapter

    private val viewModel by viewModels<HomeViewModel>()

    private var isExpanded = false

    private var supplier: Supplier? = null

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

    private fun callApiSupplier(rvSupplier: RecyclerView, shimmerFrameLayout: ShimmerFrameLayout) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getUserToken.collect { token ->
                        viewModel.setToken("Bearer $token")
                    }
                }
                launch {
                    viewModel.getSupplierList.collect { pagingData ->
                        supplierDropDownAdapter.submitData(pagingData)
                        rvSupplier.adapter = supplierDropDownAdapter

                        supplierDropDownAdapter.addLoadStateListener { loadState ->
                            when (val state = loadState.refresh) {
                                is LoadState.Loading -> {
                                    binding.apply {
                                        shimmerFrameLayout.visible()
                                        shimmerFrameLayout.startShimmer()
                                        rvSupplier.gone()
                                    }
                                }
                                is LoadState.NotLoading -> {
                                    binding.apply {
                                        shimmerFrameLayout.gone()
                                        shimmerFrameLayout.stopShimmer()
                                        rvSupplier.visible()
                                    }
                                }
                                is LoadState.Error -> {
                                    binding.apply {
                                        shimmerFrameLayout.gone()
                                        shimmerFrameLayout.stopShimmer()
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

    private fun callApiCreateItem(
        itemName: String,
        stock: String,
        price: String,
        supplier: Supplier?,
        alertDialog: AlertDialog,
        progressBar: ProgressBar
    ) {
        val supplierResponse = SupplierResponse(
            namaSupplier = supplier?.namaSupplier,
            id = supplier?.id,
            noTelp = supplier?.noTelp,
            alamat = supplier?.alamat
        )
        val data = DataItemResponse(
            harga = price.toInt(),
            supplier = supplierResponse,
            id = 0,
            namaBarang = itemName,
            stok = stock.toInt()
        )

        viewModel.setRequestBody(data)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getUserToken.collect { token ->
                        viewModel.setToken("Bearer $token")
                    }
                }
                launch {
                    viewModel.createItem.collect { uiState ->
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

    private fun setButtonClicked() {
        homeAdapter.setOnButtonClickCallback(object : HomeAdapter.OnButtonClickCallback {
            override fun onDeleteClicked(item: DataItem?) {
                customAlterDialogInformation(item)
            }

            override fun onUpdateClicked(item: DataItem?) {}
        })

        binding.imgCreate.setOnClickListener {
            customDialogCreate()
        }
    }

    private fun customAlterDialogInformation(item: DataItem?) {
        val title = resources.getString(R.string.title_alert_dialog_information)
        val subTitle =
            resources.getString(R.string.sub_title_alert_dialog_information, item?.namaBarang)
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

    private fun customDialogCreate() {
        val builder = AlertDialog.Builder(requireContext())
        val customLayout = layoutInflater.inflate(R.layout.custom_alert_dialog_create_item, null)
        builder.setView(customLayout)
        val edtItemName = customLayout.findViewById<TextView>(R.id.edt_item_name)
        val edtStock = customLayout.findViewById<TextView>(R.id.edt_stock)
        val edtPrice = customLayout.findViewById<TextView>(R.id.edt_price)
        val textInputLayout = customLayout.findViewById<TextInputLayout>(R.id.text_input_layout)
        val autoCompleteTextView =
            customLayout.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
        val shimmerAnimation = customLayout.findViewById<ShimmerFrameLayout>(R.id.shimmer_animation)
        val cvDropDownContent = customLayout.findViewById<CardView>(R.id.cv_drop_down_content)
        val rvSupplier = customLayout.findViewById<RecyclerView>(R.id.rv_supplier)
        val progressBar = customLayout.findViewById<ProgressBar>(R.id.progress_bar)
        val btnCreate = customLayout.findViewById<AppCompatButton>(R.id.btn_create)
        val btnCancel = customLayout.findViewById<AppCompatButton>(R.id.btn_cancel)
        val dialog = builder.create()
        autoCompleteTextView.setOnClickListener {
            if (!isExpanded) {
                expandedCardView(textInputLayout, cvDropDownContent)
                callApiSupplier(rvSupplier, shimmerAnimation)
            } else {
                collapseCardView(textInputLayout, cvDropDownContent)
            }
        }

        btnCreate.setOnClickListener {
            callApiCreateItem(
                edtItemName.text.toString(),
                edtStock.text.toString(),
                edtPrice.text.toString(),
                supplier,
                dialog,
                progressBar
            )
        }

        supplierDropDownAdapter.setOnItemClickCallback(object :
            SupplierDropDownAdapter.OnItemClickCallback {
            override fun onItemClicked(item: Supplier?) {
                autoCompleteTextView.setText(item?.namaSupplier)
                collapseCardView(textInputLayout, cvDropDownContent)
                supplier = item
            }
        })

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun expandedCardView(textInputLayout: TextInputLayout, cardView: CardView) {
        isExpanded = true
        cardView.visible()
        textInputLayout.endIconDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_up)
    }

    private fun collapseCardView(textInputLayout: TextInputLayout, cardView: CardView) {
        isExpanded = false
        cardView.gone()
        textInputLayout.endIconDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_down)
    }
}