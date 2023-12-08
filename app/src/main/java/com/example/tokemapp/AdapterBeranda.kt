package com.example.tokemapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView

class AdapterBeranda (val list:List<ListBunga>,val konteks: Context): RecyclerView.Adapter<AdapterBeranda.BerandaViewHolder>() {

    class BerandaViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNamaBunga = baris.findViewById<TextView>(R.id.textViewNamaBunga)
        val tvHargaBunga = baris.findViewById<TextView>(R.id.textViewHarga)
        val tvStockBunga = baris.findViewById<TextView>(R.id.textViewStok)
        val lyNumber = baris.findViewById<NumberPicker>(R.id.numberPicker)
        val gambarBunga = baris.findViewById<ImageView>(R.id.GambarBunga)
        val btnTambah = baris.findViewById<Button>(R.id.buttonTambah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerandaViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_bunga,parent,false)
        return BerandaViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BerandaViewHolder, position: Int) {
        val binding = list[position]

        holder.tvNamaBunga.text = binding.nama
        holder.tvHargaBunga.text = binding.harga.toString()
        holder.tvStockBunga.text = binding.stok.toString()
        holder.gambarBunga.setImageResource(binding.gambarResId)
        val angka = holder.lyNumber.value


    }
}