package com.app.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(

	@field:SerializedName("data")
	val data: List<DataItemResponse>? = null,

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

data class DataItemResponse(

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("supplier")
	val supplier: SupplierResponse? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namaBarang")
	val namaBarang: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null
)

data class SupplierResponse(

	@field:SerializedName("namaSupplier")
	val namaSupplier: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
