package com.app.miniproject.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.app.miniproject.domain.interfaces.ShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val shopUseCase: ShopUseCase) : ViewModel() {
    val getUserName: Flow<String> = shopUseCase.getUserName()
    fun deleteLocalData() {
        CoroutineScope(Dispatchers.IO).launch {
            shopUseCase.deleteLocalData()
        }
    }
}