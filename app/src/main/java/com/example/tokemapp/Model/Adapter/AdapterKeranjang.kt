package com.example.tokemapp.Model.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tokemapp.Model.KeranjangModel
import com.example.tokemapp.R

class AdapterKeranjang(val listKeranjang: MutableList<KeranjangModel>,val konteks: Context): RecyclerView.Adapter<AdapterKeranjang.KeranjangViewHolder>() {

    class KeranjangViewHolder(baris : View):RecyclerView.ViewHolder(baris){

        val tvNamaBunga = baris.findViewById<TextView>(R.id.tvNamaBungaKeranjang)
        val tvHarga = baris.findViewById<TextView>(R.id.tvHargaBungaKeranjang)
        val tvPembeli = baris.findViewById<TextView>(R.id.tvNamaPembeliKeranjang)
        val gambar = baris.findViewById<ImageView>(R.id.gambarBunganKeranjang)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_keranjang,parent,false)
        return KeranjangViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return listKeranjang.size
    }

    override fun onBindViewHolder(holder: KeranjangViewHolder, position: Int) {
        val bind = listKeranjang[position]
        holder.tvPembeli.text = bind.namaPembeli
        holder.tvNamaBunga.text = bind.namaBunga
        holder.tvHarga.text = bind.hargaBUnga.toString()

        Glide.with(konteks).load(bind.gambar).placeholder(R.drawable.lili).into(holder.gambar)

    }
}