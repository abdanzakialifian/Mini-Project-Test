package com.app.miniproject.presentation.supplier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.miniproject.data.source.remote.response.SupplierResponse
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.CreateSupplier
import com.app.miniproject.domain.model.Delete
import com.app.miniproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {
    private val data = MutableStateFlow(SupplierResponse())
    private val authorization = MutableStateFlow("")
    private val id = MutableStateFlow(0)

    fun setToken(authorization: String) {
        this.authorization.value = authorization
    }

    fun setData(data: SupplierResponse) {
        this.data.value = data
    }

    fun setId(id: Int) {
        this.id.value = id
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val deleteSupplier: Flow<UiState<Delete>> = id.flatMapLatest { id ->
        authorization.flatMapLatest { authorization ->
            shopUseCase.deleteSupplier(id, authorization).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UiState.Loading
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val createSupplier: Flow<UiState<CreateSupplier>> =
        authorization.flatMapLatest { authorization ->
            data.flatMapLatest { data ->
                shopUseCase.createSupplier(data, authorization).stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = UiState.Loading
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val updateItem: Flow<UiState<CreateSupplier>> = authorization.flatMapLatest { authorization ->
        data.flatMapLatest { data ->
            id.flatMapLatest { id ->
                shopUseCase.updateSupplier(id, authorization, data).stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = UiState.Loading
                )
            }
        }
    }
}