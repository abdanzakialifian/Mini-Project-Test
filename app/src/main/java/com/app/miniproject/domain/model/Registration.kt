package com.app.miniproject.domain.model

data class Registration(
    val data: Data? = null,
    val message: String? = null,
    val status: String? = null
)

data class Data(
    val profileName: String? = null,
    val id: Int? = null,
    val username: String? = null
)
