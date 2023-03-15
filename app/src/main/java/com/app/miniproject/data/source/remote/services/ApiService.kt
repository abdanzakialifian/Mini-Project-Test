package com.app.miniproject.data.source.remote.services

import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.data.source.remote.response.ItemResponse
import com.app.miniproject.data.source.remote.response.LoginResponse
import com.app.miniproject.data.source.remote.response.RegistrationResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun postLogin(
        @Body requestBody: RequestBody
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun postRegistration(
        @Body requestBody: RequestBody
    ): Response<RegistrationResponse>

    @GET("barang/find-all")
    suspend fun getItemsList(
        @Query("offset") offsite: Int,
        @Query("limit") limit: Int,
        @Header("Authorization") authorization: String
    ): ItemResponse

    @GET("supplier/find-all")
    suspend fun getSuppliersList(
        @Query("offset") offsite: Int,
        @Query("limit") limit: Int,
        @Header("Authorization") authorization: String
    )

    @FormUrlEncoded
    @POST("barang/create")
    fun postItems(
        @Query("namaBarang") itemName: String,
        @Body requestBody: RequestBody,
        @Header("Authorizations") authorizations: String
    )
}