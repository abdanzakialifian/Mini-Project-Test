package com.app.miniproject.data.source.remote

import com.app.miniproject.data.source.remote.response.RegistrationResponse
import com.app.miniproject.data.source.remote.services.ApiService
import com.app.miniproject.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun postRegistration(requestBody: RequestBody): Flow<UiState<RegistrationResponse>> = flow {
        emit(UiState.Loading)
        val response = apiService.postRegistration(requestBody)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            emit(UiState.Success(responseBody))
        } else {
            emit(UiState.Error(responseBody?.message.toString()))
        }
    }
}