package com.example.tokemapp.Model

data class UserModel(
    val id: String ="isisisis",
    val email: String = "test@gmail.com",
    val nama: String = "test",
    val password: String ="12345678",
    val tanggalLahir : String = "Madiun, 16 Agustus 2005"
)

data class UserUpdate(
    val email: String = "test@gmail.com",
    val nama: String = "test",
    val password: String ="12345678",
    val tanggalLahir : String = "Madiun, 16 Agustus 2005"
)
