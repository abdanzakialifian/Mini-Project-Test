package com.app.miniproject.presentation.registration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.Registration
import com.app.miniproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val shopUseCase: ShopUseCase) :
    ViewModel() {

    fun postRegister(requestBody: RequestBody): StateFlow<UiState<Registration>> =
        shopUseCase.postRegistration(requestBody).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = UiState.Loading
        )
}