package com.example.tokemapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BerandaActivity : AppCompatActivity(){

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)
        bottomNav = findViewById(R.id.bottomNavigationView)
        val berandaFrag = BerandaFragment()
        val keranjangFrag = KeranjangFragment()
        val inboxFrag = InboxFragment()
        val accountFrag = AccountFragment()

        bottomNav.setOnItemReselectedListener {
            when(it.itemId){
                R.id.beranda -> changeFragment(berandaFrag)
                R.id.keranjang -> changeFragment(keranjangFrag)
                R.id.inbox -> changeFragment(inboxFrag)
                R.id.akun -> changeFragment(accountFrag)

                else -> {
                    val gagal =Intent(this,BerandaActivity::class.java)
                    startActivity(gagal)
                }
            }

            true
        }






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

    fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaksi = fragmentManager.beginTransaction()

        transaksi.replace(R.id.fragmentContainerView,fragment)
        transaksi.commit()
    }

}
