package com.example.tokemapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

    class LoginActivity : AppCompatActivity() {

        private lateinit var usernameEditText: EditText
        private lateinit var passwordEditText: EditText
        private lateinit var loginButton: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            usernameEditText = findViewById(R.id.editTextUsername)
            passwordEditText = findViewById(R.id.editTextPassword)
            loginButton = findViewById(R.id.buttonLogin)

            loginButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (isValidCredentials(username, password)) {
                    // Jika login berhasil, pindah ke aktivitas berikutnya
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Jika login gagal, tampilkan pesan kesalahan
                    Toast.makeText(this, "Login gagal. Coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Fungsi untuk melakukan validasi username dan password (contoh sederhana)
        private fun isValidCredentials(username: String, password: String): Boolean {
            return username == "user" && password == "password"
        }
    }
