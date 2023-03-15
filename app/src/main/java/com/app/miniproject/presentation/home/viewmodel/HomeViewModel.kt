package com.app.miniproject.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.DataItem
import com.app.miniproject.domain.model.Delete
import com.app.miniproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {
    private val authorization = MutableStateFlow("")
    private val id = MutableStateFlow(0)

    fun setToken(authorization: String) {
        this.authorization.value = authorization
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
}