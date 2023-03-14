package com.app.miniproject.domain.usecase

import com.app.miniproject.domain.interfaces.ShopRepository
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.Registration
import com.app.miniproject.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopUseCaseImpl @Inject constructor(private val shopRepository: ShopRepository) :
    ShopUseCase {
    override fun postRegistration(requestBody: RequestBody): Flow<UiState<Registration>> =
        shopRepository.postRegistration(requestBody)
}