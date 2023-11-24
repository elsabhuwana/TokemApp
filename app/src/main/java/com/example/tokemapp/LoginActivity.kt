package com.example.tokemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tokemapp.MainActivity
import com.example.tokemapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class LoginActivity : AppCompatActivity() {
    val db = Firebase.firestore

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonRegistrasi)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValidCredentials(email, password)) {
                // Jika login berhasil, pindah ke aktivitas berikutnya (misalnya, MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Jika login gagal, tampilkan pesan kesalahan
                Toast.makeText(this, "Login gagal. Coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            // Pindah ke halaman registrasi jika tombol "Registrasi" ditekan
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        // Implementasikan validasi email dan password sesuai kebutuhan
        // melakukan validasi dengan membandingkan email dan password dengan data yang benar
        return email == "contoh@email.com" && password == "password123"
    }
}
