package com.app.miniproject.domain.interfaces

import androidx.paging.PagingData
import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.data.source.remote.response.SupplierResponse
import com.app.miniproject.domain.model.*
import com.app.miniproject.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface ShopUseCase {
    fun postRegistration(requestBody: RequestBody): Flow<UiState<Registration>>
    fun postLogin(requestBody: RequestBody): Flow<UiState<Login>>
    suspend fun saveUserSession(isLogin: Boolean)
    fun getUserSession(): Flow<Boolean>
    suspend fun saveUserToken(token: String)
    fun getUserToken(): Flow<String>
    fun getItemsList(authorization: String): Flow<PagingData<DataItem>>
    fun getSupplierList(authorization: String): Flow<PagingData<Supplier>>
    fun deleteItem(id: Int, authorization: String): Flow<UiState<Delete>>
    fun deleteSupplier(id: Int, authorization: String): Flow<UiState<Delete>>
    fun createItem(data: DataItemResponse, authorization: String): Flow<UiState<CreateItem>>
    fun createSupplier(data: SupplierResponse, authorization: String): Flow<UiState<CreateSupplier>>
}