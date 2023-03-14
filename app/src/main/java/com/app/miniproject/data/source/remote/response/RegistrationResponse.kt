package com.app.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(

	@field:SerializedName("data")
	val data: DataResponse? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataResponse(

	@field:SerializedName("profileName")
	val profileName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
