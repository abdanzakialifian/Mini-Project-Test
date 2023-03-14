package com.app.miniproject.data.repository

import com.app.miniproject.data.source.remote.RemoteDataSource
import com.app.miniproject.domain.interfaces.ShopRepository
import com.app.miniproject.domain.model.Registration
import com.app.miniproject.utils.UiState
import com.app.miniproject.utils.toRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    ShopRepository {
    override fun postRegistration(requestBody: RequestBody): Flow<UiState<Registration>> =
        remoteDataSource.postRegistration(requestBody).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toRegistration())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }
}