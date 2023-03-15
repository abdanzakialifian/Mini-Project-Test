package com.app.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SupplierDataResponse(
    @field:SerializedName("data")
    val data: List<SupplierResponse>? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("total_page")
    val totalPage: Int? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("total_record")
    val totalRecord: Int? = null
)