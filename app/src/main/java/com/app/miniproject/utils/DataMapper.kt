package com.app.miniproject.utils

import com.app.miniproject.data.source.remote.response.RegistrationResponse
import com.app.miniproject.domain.model.Data
import com.app.miniproject.domain.model.Registration

fun RegistrationResponse.toRegistration(): Registration {
    val data = Data(
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