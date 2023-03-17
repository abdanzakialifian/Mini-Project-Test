package com.app.miniproject.presentation.buyer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.miniproject.data.source.remote.response.DataBuyerResponse
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.CreateBuyer
import com.app.miniproject.domain.model.DataBuyer
import com.app.miniproject.domain.model.Delete
import com.app.miniproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {
    private val data = MutableStateFlow(DataBuyerResponse())
    private val authorization = MutableStateFlow("")
    private val id = MutableStateFlow(0)

    fun setToken(authorization: String) {
        this.authorization.value = authorization
    }

    fun setData(data: DataBuyerResponse) {
        this.data.value = data
    }

    fun setId(id: Int) {
        this.id.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getBuyerList: Flow<PagingData<DataBuyer>> = authorization.flatMapLatest { authorization ->
        shopUseCase.getBuyerList(authorization).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
    }

    val getUserToken: Flow<String> = shopUseCase.getUserToken()

    @OptIn(ExperimentalCoroutinesApi::class)
    val deleteBuyer: Flow<UiState<Delete>> = id.flatMapLatest { id ->
        authorization.flatMapLatest { authorization ->
            shopUseCase.deleteBuyer(id, authorization).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UiState.Loading
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val createBuyer: Flow<UiState<CreateBuyer>> = authorization.flatMapLatest { authorization ->
        data.flatMapLatest { data ->
            shopUseCase.createBuyer(data, authorization).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UiState.Loading
            )
        }
    }
}