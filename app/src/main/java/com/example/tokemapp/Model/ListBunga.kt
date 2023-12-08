package com.example.tokemapp.Model

data class ListBunga(
    val nama: String,
    val gambarResId: Int,
    val deskripsi: String,
    val harga: Int,
    val stok: Int,
    var counter: Int = 0
)