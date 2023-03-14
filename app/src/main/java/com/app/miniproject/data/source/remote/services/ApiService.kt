package com.app.miniproject.data.source.remote.services

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
    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Body requestBody: RequestBody
    )

    @POST("auth/register")
    suspend fun postRegistration(
        @Body requestBody: RequestBody
    ): Response<RegistrationResponse>

    @GET("barang/find-all")
    fun getItemsList(
        @Query("Offsite") offsite: Int,
        @Body requestBody: RequestBody,
        @Header("Authorizations") authorizations: String
    )

    @GET("supplier/find-all")
    fun getSuppliersList(
        @Query("Offsite") offsite: Int,
        @Body requestBody: RequestBody,
        @Header("Authorizations") authorizations: String
    )

    @FormUrlEncoded
    @POST("barang/create")
    fun postItems(
        @Query("namaBarang") itemName: String,
        @Body requestBody: RequestBody,
        @Header("Authorizations") authorizations: String
    )
}