package com.app.miniproject.utils

import com.app.miniproject.data.source.remote.response.*
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
}

fun SupplierResponse.toSupplier(): Supplier = Supplier(
    namaSupplier = this.namaSupplier,
    id = this.id,
    noTelp = this.noTelp,
    alamat = this.alamat
)

fun DeleteResponse.toDelete(): Delete = Delete(message = this.message, status = this.status)