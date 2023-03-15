package com.app.miniproject.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "shop")

@Singleton
class ShopDataStore @Inject constructor(@ApplicationContext context: Context) {
    private val shopDataStore = context.dataStore

    suspend fun saveUserSession(isLogin: Boolean) {
        shopDataStore.edit { preference ->
            preference[USER_SESSION] = isLogin
        }
    }

    fun getUserSession(): Flow<Boolean> = shopDataStore.data.map { preference ->
        preference[USER_SESSION] ?: false
    }

    suspend fun saveUserToken(token: String) {
        shopDataStore.edit { preference ->
            preference[USER_TOKEN] = token
        }
    }

    fun getUserToken(): Flow<String> = shopDataStore.data.map { preference ->
        preference[USER_TOKEN] ?: ""
    }

    companion object {
        private val USER_SESSION = booleanPreferencesKey("user_session")
        private val USER_TOKEN = stringPreferencesKey("user_token")
    }
}