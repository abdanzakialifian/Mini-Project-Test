package com.app.miniproject.data.source.local

import com.app.miniproject.data.source.local.datastore.ShopDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val shopDataStore: ShopDataStore) {
    suspend fun saveUserSession(isLogin: Boolean) {
        shopDataStore.saveUserSession(isLogin)
    }

    fun getUserSession(): Flow<Boolean> = shopDataStore.getUserSession()
    suspend fun saveUserToken(token: String) {
        shopDataStore.saveUserToken(token)
    }

    fun getUserToken(): Flow<String> = shopDataStore.getUserToken()
}