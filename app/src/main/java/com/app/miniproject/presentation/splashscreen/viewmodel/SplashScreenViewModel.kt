package com.app.miniproject.presentation.splashscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.miniproject.domain.interfaces.ShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val shopUseCase: ShopUseCase) :
    ViewModel() {
    val getUserSession: StateFlow<Boolean> = shopUseCase.getUserSession().stateIn(
        initialValue = false,
        started = SharingStarted.WhileSubscribed(),
        scope = viewModelScope
    )
}