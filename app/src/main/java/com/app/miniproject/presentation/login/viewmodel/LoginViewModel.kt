package com.app.miniproject.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.Login
import com.app.miniproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {

    fun postLogin(requestBody: RequestBody): StateFlow<UiState<Login>> =
        shopUseCase.postLogin(requestBody).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = UiState.Loading
        )

    fun saveUserSession(isLogin: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            shopUseCase.saveUserSession(isLogin)
        }
    }
}