package com.app.miniproject.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.miniproject.data.source.remote.paging.BuyerPagingSource
import com.app.miniproject.data.source.remote.paging.ItemPagingSource
import com.app.miniproject.data.source.remote.paging.SupplierPagingSource
import com.app.miniproject.data.source.remote.response.*
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
    private val itemPagingSource: ItemPagingSource,
    private val supplierPagingSource: SupplierPagingSource,
    private val buyerPagingSource: BuyerPagingSource
) {
    fun postRegistration(requestBody: RequestBody): Flow<UiState<RegistrationResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.postRegistration(requestBody)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun postLogin(requestBody: RequestBody): Flow<UiState<LoginResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.postLogin(requestBody)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
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

    fun getSupplierList(authorization: String): Flow<PagingData<SupplierResponse>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            supplierPagingSource.apply {
                setToken(authorization)
            }
        }
    ).flow

    fun deleteItem(id: Int, authorization: String): Flow<UiState<DeleteResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.deleteItem(id, authorization)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun deleteSupplier(id: Int, authorization: String): Flow<UiState<DeleteResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.deleteSupplier(id, authorization)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun createItem(
        data: DataItemResponse,
        authorization: String
    ): Flow<UiState<CreateItemResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.createItem(data, authorization)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun createSupplier(
        data: SupplierResponse,
        authorization: String
    ): Flow<UiState<CreateSupplierResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.createSupplier(data, authorization)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun updateItem(
        id: Int,
        authorization: String,
        data: DataItemResponse
    ): Flow<UiState<CreateItemResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.updateItem(id, authorization, data)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun updateSupplier(
        id: Int,
        authorization: String,
        data: SupplierResponse
    ): Flow<UiState<CreateSupplierResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.updateSupplier(id, authorization, data)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getBuyerList(authorization: String): Flow<PagingData<DataBuyerResponse>> =
        Pager(config = PagingConfig(
            pageSize = 10, initialLoadSize = 10
        ), pagingSourceFactory = {
            buyerPagingSource.apply {
                setToken(authorization)
            }
        }).flow

    fun deleteBuyer(id: Int, authorization: String): Flow<UiState<DeleteResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.deleteBuyer(id, authorization)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun createBuyer(
        data: DataBuyerResponse,
        authorization: String
    ): Flow<UiState<CreateBuyerResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.createBuyer(data, authorization)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                emit(UiState.Success(responseBody))
            } else {
                emit(UiState.Error(responseBody?.message.toString()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }
}