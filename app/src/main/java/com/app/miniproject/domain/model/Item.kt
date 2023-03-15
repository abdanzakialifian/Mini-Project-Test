package com.app.miniproject.domain.model

data class Item(
    val data: List<DataItem>? = null,
    val limit: Int? = null,
    val totalPage: Int? = null,
    val page: Int? = null,
    val message: String? = null,
    val status: String? = null,
    val totalRecord: Int? = null
)

data class DataItem(
    val harga: Int? = null,
    val supplier: Supplier? = null,
    val id: Int? = null,
    val namaBarang: String? = null,
    val stok: Int? = null
)

data class Supplier(
    val namaSupplier: String? = null,
    val id: Int? = null,
    val noTelp: String? = null,
    val alamat: String? = null
)