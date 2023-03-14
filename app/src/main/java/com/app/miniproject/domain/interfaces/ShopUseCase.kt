package com.app.miniproject.domain.interfaces

import com.app.miniproject.domain.model.Registration
import com.app.miniproject.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface ShopUseCase {
    fun postRegistration(requestBody: RequestBody): Flow<UiState<Registration>>
}