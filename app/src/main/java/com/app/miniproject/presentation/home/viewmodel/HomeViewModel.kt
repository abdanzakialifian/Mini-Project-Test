package com.app.miniproject.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.CreateItem
import com.app.miniproject.domain.model.DataItem
import com.app.miniproject.domain.model.Delete
import com.app.miniproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {
    private val data = MutableStateFlow(DataItemResponse())
    private val authorization = MutableStateFlow("")
    private val id = MutableStateFlow(0)

    fun setToken(authorization: String) {
        this.authorization.value = authorization
    }

    fun setData(data: DataItemResponse) {
        this.data.value = data
    }

    fun setId(id: Int) {
        this.id.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getItemsList: Flow<PagingData<DataItem>> = authorization.flatMapLatest { authorization ->
        shopUseCase.getItemsList(authorization).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
    }

    val getUserToken: Flow<String> = shopUseCase.getUserToken()

    @OptIn(ExperimentalCoroutinesApi::class)
    val deleteItem: Flow<UiState<Delete>> = id.flatMapLatest { id ->
        authorization.flatMapLatest { authorization ->
            shopUseCase.deleteItem(id, authorization).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UiState.Loading
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getSupplierList = authorization.flatMapLatest { authorization ->
        shopUseCase.getSupplierList(authorization).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val createItem: Flow<UiState<CreateItem>> = authorization.flatMapLatest { authorization ->
        data.flatMapLatest { data ->
            shopUseCase.createItem(data, authorization).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UiState.Loading
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val updateItem: Flow<UiState<CreateItem>> = authorization.flatMapLatest { authorization ->
        data.flatMapLatest { data ->
            id.flatMapLatest { id ->
                shopUseCase.updateItem(id, authorization, data).stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = UiState.Loading
                )
            }
        }
    }
}