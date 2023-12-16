package com.example.tokemapp.View.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tokemapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    val db = Firebase.firestore
    lateinit var firebaseAuthReg: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuthReg = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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

                // Tambahkan logika untuk menyimpan data pengguna ke database
                firebaseAuthReg.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    addDataUserToFirestore(email,password)
                }.addOnFailureListener {
                    Toast.makeText(this, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()

                }
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

    fun addDataUserToFirestore(email: String,password: String){
        val userMap = hashMapOf(
            "email" to email,
            "nama" to "",
            "password" to password,
            "tanggalLahir" to ""
        )

        firestore.collection("user").add(userMap).addOnSuccessListener {
            Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
            val pindah = Intent(this, LoginActivity::class.java)
            startActivity(pindah)
        }.addOnFailureListener {
            Toast.makeText(this, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()
        }
    }
}
