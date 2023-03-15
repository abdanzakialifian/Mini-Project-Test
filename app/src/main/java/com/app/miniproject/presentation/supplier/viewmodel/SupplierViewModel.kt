package com.app.miniproject.presentation.supplier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.miniproject.domain.interfaces.ShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {
    private val authorization = MutableStateFlow("")

    fun setToken(authorization: String) {
        this.authorization.value = authorization
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getSupplierList = authorization.flatMapLatest { authorization ->
        shopUseCase.getSupplierList(authorization).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
    }

    val getUserToken: Flow<String> = shopUseCase.getUserToken()
}