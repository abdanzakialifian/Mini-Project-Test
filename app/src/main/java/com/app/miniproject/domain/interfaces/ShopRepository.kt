package com.app.miniproject.domain.interfaces

import com.app.miniproject.domain.model.Login
import com.app.miniproject.domain.model.Registration
import com.app.miniproject.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface ShopRepository {
    fun postRegistration(requestBody: RequestBody): Flow<UiState<Registration>>
    fun postLogin(requestBody: RequestBody): Flow<UiState<Login>>
    suspend fun saveUserSession(isLogin: Boolean)
    fun getUserSession(): Flow<Boolean>
    suspend fun saveUserToken(token: String)
    fun getUserToken(): Flow<String>
}