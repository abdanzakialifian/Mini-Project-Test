package com.app.miniproject.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.app.miniproject.data.source.local.LocalDataSource
import com.app.miniproject.data.source.remote.RemoteDataSource
import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.data.source.remote.response.SupplierResponse
import com.app.miniproject.domain.interfaces.ShopRepository
import com.app.miniproject.domain.model.*
import com.app.miniproject.utils.*
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

    override suspend fun saveUserToken(token: String) {
        localDataSource.saveUserToken(token)
    }

    override fun getUserToken(): Flow<String> = localDataSource.getUserToken()

    override fun getItemsList(authorization: String): Flow<PagingData<DataItem>> =
        remoteDataSource.getItemsList(authorization).map { pagingData ->
            pagingData.map { map ->
                map.toDataItem()
            }
        }

    override fun getSupplierList(authorization: String): Flow<PagingData<Supplier>> =
        remoteDataSource.getSupplierList(authorization).map { pagingData ->
            pagingData.map { map ->
                map.toSupplier()
            }
        }

    override fun deleteItem(id: Int, authorization: String): Flow<UiState<Delete>> =
        remoteDataSource.deleteItem(id, authorization).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toDelete())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override fun deleteSupplier(id: Int, authorization: String): Flow<UiState<Delete>> =
        remoteDataSource.deleteSupplier(id, authorization).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toDelete())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override fun createItem(
        data: DataItemResponse,
        authorization: String
    ): Flow<UiState<CreateItem>> =
        remoteDataSource.createItem(data, authorization).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toCreateItem())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override fun createSupplier(
        data: SupplierResponse,
        authorization: String
    ): Flow<UiState<CreateSupplier>> =
        remoteDataSource.createSupplier(data, authorization).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toCreateSupplier())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override fun updateItem(
        id: Int,
        authorization: String,
        data: DataItemResponse
    ): Flow<UiState<CreateItem>> =
        remoteDataSource.updateItem(id, authorization, data).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toCreateItem())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }

    override fun updateSupplier(
        id: Int,
        authorization: String,
        data: SupplierResponse
    ): Flow<UiState<CreateSupplier>> =
        remoteDataSource.updateSupplier(id, authorization, data).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> UiState.Success(uiState.data.toCreateSupplier())
                is UiState.Error -> UiState.Error(uiState.message)
            }
        }
}