package com.app.miniproject.domain.model

data class Login(
    val data: LoginData? = null,
    val message: String? = null,
    val status: String? = null
)

data class LoginData(
    val profileName: String? = null,
    val id: Int? = null,
    val username: String? = null,
    val token: String? = null
)
