package com.example.tokemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tokemapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    val db = Firebase.firestore


    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword)
        registerButton = findViewById(R.id.buttonRegister)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (isValidRegistration(email, password, confirmPassword)) {
                // Simpan data pendaftaran atau pindah ke aktivitas lain
                Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                // Tambahkan logika untuk menyimpan data pengguna ke database
            } else {
                // Jika pendaftaran gagal, tampilkan pesan kesalahan
                Toast.makeText(this, "Pendaftaran gagal. Pastikan semua isian benar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidRegistration(email: String, password: String, confirmPassword: String): Boolean {
        // Implementasikan validasi email, password, dan konfirmasi password sesuai kebutuhan
        // Anda bisa melakukan validasi seperti memeriksa apakah email valid dan apakah password dan konfirmasi password sesuai
        return email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword
    }
}
