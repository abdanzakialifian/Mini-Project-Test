package com.app.miniproject.utils

import com.app.miniproject.data.source.remote.response.DataItemResponse
import com.app.miniproject.data.source.remote.response.ItemResponse
import com.app.miniproject.data.source.remote.response.LoginResponse
import com.app.miniproject.data.source.remote.response.RegistrationResponse
import com.app.miniproject.domain.model.*

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

fun DataItemResponse.toDataItem(): DataItem {
//    val listData = this.data?.map {
        val supplier = Supplier(
            namaSupplier = this.supplier?.namaSupplier,
            id = this.supplier?.id,
            noTelp = this.supplier?.noTelp,
            alamat = this.supplier?.alamat
        )
        return DataItem(
            harga = this.harga,
            supplier = supplier,
            id = this.id,
            namaBarang = this.namaBarang,
            stok = this.stok
        )
//    }

//    return Item(
//        data = listData,
//        limit = this.limit,
//        totalPage = this.totalPage,
//        page = this.page,
//        message = this.message,
//        status = this.status,
//        totalRecord = this.totalRecord
//    )
}