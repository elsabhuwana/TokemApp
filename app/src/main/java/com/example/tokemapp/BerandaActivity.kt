package com.example.tokemapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
class BerandaActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        recyclerView = findViewById(R.id.recyclerView)




        // Inisialisasi daftar bunga
        val listBunga = listOf(
            ListBunga("Mawar", R.drawable.mawarputih, "Deskripsi Mawar]", 10000, 20),
            ListBunga("Tulip", R.drawable.tulip, "Deskripsi Tulip", 12000, 15),
            ListBunga("Mawar", R.drawable.mawarmerah, "Deskripsi Mawar", 10000, 20),
            ListBunga("Mawar", R.drawable.matahari, "Deskripsi Mawar", 10000, 20),
            ListBunga("Mawar", R.drawable.mawarputih, "Deskripsi Mawar", 10000, 20),
            ListBunga("Mawar", R.drawable.pinktulip, "Deskripsi Mawar", 10000, 20),
            ListBunga("Mawar", R.drawable.chamomile, "Deskripsi Mawar", 10000, 20),
            ListBunga("Mawar", R.drawable.lili, "Deskripsi Mawar", 10000, 20)
        )

        // Set daftar bunga ke adapter

    }

//    override fun onItemClick(flower: ListBunga, quantity: Int) {
//        // Implementasikan logika ketika item diklik
//        // Contoh: Tambahkan bunga ke keranjang, tampilkan dialog, dll.
//        Toast.makeText(this, "Bunga ${flower.nama} ditambahkan ke keranjang ($quantity pcs)", Toast.LENGTH_SHORT).show()
//    }
}
