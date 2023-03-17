package com.app.miniproject.presentation.splashscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.app.miniproject.domain.interfaces.ShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(shopUseCase: ShopUseCase) :
    ViewModel() {
    val getUserSession: Flow<Boolean> = shopUseCase.getUserSession()
}