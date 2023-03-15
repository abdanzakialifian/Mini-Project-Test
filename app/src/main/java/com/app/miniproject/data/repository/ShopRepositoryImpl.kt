package com.app.miniproject.data.repository

import com.app.miniproject.data.source.local.LocalDataSource
import com.app.miniproject.data.source.remote.RemoteDataSource
import com.app.miniproject.domain.interfaces.ShopRepository
import com.app.miniproject.domain.model.Login
import com.app.miniproject.domain.model.Registration
import com.app.miniproject.utils.UiState
import com.app.miniproject.utils.toLogin
import com.app.miniproject.utils.toRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    ShopRepository {
    override fun postRegistration(requestBody: RequestBody): Flow<UiState<Registration>> =
        remoteDataSource.postRegistration(requestBody).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toRegistration())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override fun postLogin(requestBody: RequestBody): Flow<UiState<Login>> =
        remoteDataSource.postLogin(requestBody).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toLogin())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override suspend fun saveUserSession(isLogin: Boolean) {
        localDataSource.saveUserSession(isLogin)
    }

    override fun getUserSession(): Flow<Boolean> = localDataSource.getUserSession()
}