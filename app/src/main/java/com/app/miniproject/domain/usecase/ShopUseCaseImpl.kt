package com.app.miniproject.domain.usecase

import androidx.paging.PagingData
import com.app.miniproject.domain.interfaces.ShopRepository
import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.model.DataItem
import com.app.miniproject.domain.model.Item
import com.app.miniproject.domain.model.Login
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

    override fun postLogin(requestBody: RequestBody): Flow<UiState<Login>> =
        shopRepository.postLogin(requestBody)

    override suspend fun saveUserSession(isLogin: Boolean) {
        shopRepository.saveUserSession(isLogin)
    }

    override fun getUserSession(): Flow<Boolean> = shopRepository.getUserSession()
    override suspend fun saveUserToken(token: String) {
        shopRepository.saveUserToken(token)
    }

    override fun getUserToken(): Flow<String> = shopRepository.getUserToken()
    override fun getItemsList(authorization: String): Flow<PagingData<DataItem>> =
        shopRepository.getItemsList(authorization)
}