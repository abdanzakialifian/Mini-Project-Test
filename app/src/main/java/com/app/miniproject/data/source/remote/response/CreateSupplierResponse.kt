package com.app.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreateSupplierResponse(
    @field:SerializedName("data")
    val data: SupplierResponse? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
