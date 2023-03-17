package com.app.miniproject.domain.model

data class Buyer(
    val data: List<DataBuyer>? = null,
    val limit: Int? = null,
    val totalPage: Int? = null,
    val page: Int? = null,
    val message: String? = null,
    val status: String? = null,
    val totalRecord: Int? = null
)

data class DataBuyer(
    val namaPembeli: String? = null,
    val jenisKelamin: String? = null,
    val id: Int? = null,
    val noTelp: String? = null,
    val alamat: String? = null
)