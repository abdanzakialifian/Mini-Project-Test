package com.app.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class BuyerResponse(

	@field:SerializedName("data")
	val data: List<DataBuyerResponse>? = null,

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

data class DataBuyerResponse(

	@field:SerializedName("namaPembeli")
	val namaPembeli: String? = null,

	@field:SerializedName("jenisKelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
