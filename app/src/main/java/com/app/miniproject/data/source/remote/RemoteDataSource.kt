package com.app.miniproject.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.miniproject.data.source.remote.paging.ItemPagingSource
import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.data.source.remote.response.ItemResponse
import com.app.miniproject.data.source.remote.response.LoginResponse
import com.app.miniproject.data.source.remote.response.RegistrationResponse
import com.app.miniproject.data.source.remote.services.ApiService
import com.app.miniproject.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val itemPagingSource: ItemPagingSource
) {
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

    fun postLogin(requestBody: RequestBody): Flow<UiState<LoginResponse>> = flow {
        emit(UiState.Loading)
        val response = apiService.postLogin(requestBody)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            emit(UiState.Success(responseBody))
        } else {
            emit(UiState.Error(responseBody?.message.toString()))
        }
    }

    fun getItemsList(authorization: String): Flow<PagingData<DataItemResponse>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            itemPagingSource.apply {
                setToken(authorization)
            }
        }
    ).flow
}