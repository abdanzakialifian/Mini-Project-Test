package com.app.miniproject.utils

import com.app.miniproject.data.source.remote.response.LoginResponse
import com.app.miniproject.data.source.remote.response.RegistrationResponse
import com.app.miniproject.domain.model.Login
import com.app.miniproject.domain.model.LoginData
import com.app.miniproject.domain.model.Registration
import com.app.miniproject.domain.model.RegistrationData

fun RegistrationResponse.toRegistration(): Registration {
    val data = RegistrationData(
        profileName = this.data?.profileName,
        id = this.data?.id,
        username = this.data?.username
    )
    return Registration(
        data = data,
        message = this.message,
        status = this.status
    )
}

fun LoginResponse.toLogin(): Login {
    val data = LoginData(
        profileName = this.data?.profileName,
        id = this.data?.id,
        username = this.data?.username,
        token = this.data?.token
    )
    return Login(data = data, message = this.message, status = this.status)
}