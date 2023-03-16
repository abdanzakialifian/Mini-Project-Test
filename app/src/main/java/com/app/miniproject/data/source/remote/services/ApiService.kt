package com.app.miniproject.data.source.remote.services

import com.app.miniproject.data.source.remote.response.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
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
    ): SupplierDataResponse

    @DELETE("barang/delete/{id}")
    suspend fun deleteItem(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Response<DeleteResponse>

    @DELETE("supplier/delete/{id}")
    suspend fun deleteSupplier(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Response<DeleteResponse>

    @POST("barang/create")
    suspend fun createItem(
        @Body data: DataItemResponse,
        @Header("Authorization") authorization: String
    ): Response<CreateItemResponse>

    @POST("supplier/create")
    suspend fun createSupplier(
        @Body data: SupplierResponse,
        @Header("Authorization") authorization: String
    ): Response<CreateSupplierResponse>
}