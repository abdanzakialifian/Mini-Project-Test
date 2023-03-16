package com.app.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreateItemResponse(

	@field:SerializedName("data")
	val data: DataItemResponse? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)