package com.example.tokemapp

interface BungaItemClickListener {
    fun onItemClick(flower: ListBunga, quantity: Int)
}

open class BungaItemClickListenerImpl : BungaItemClickListener {
    override fun onItemClick(flower: ListBunga, quantity: Int) {
        // Implementasi logika ketika item diklik
        // Contoh: Tambahkan bunga ke keranjang, tampilkan dialog, dll.
    }
}