package com.example.tokemapp.Model.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokemapp.Model.ListBunga
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.BungaViewModel

class AdapterBeranda (val list:List<ListBunga>, val konteks: Context, val viewmodel: BungaViewModel): RecyclerView.Adapter<AdapterBeranda.BerandaViewHolder>() {

    class BerandaViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNamaBunga = baris.findViewById<TextView>(R.id.textViewNamaBunga)
        val tvHargaBunga = baris.findViewById<TextView>(R.id.textViewHarga)
        val tvStockBunga = baris.findViewById<TextView>(R.id.textViewStok)
        val gambarBunga = baris.findViewById<ImageView>(R.id.GambarBunga)
        val btnTambah = baris.findViewById<Button>(R.id.buttonTambah)
        val incrementButton = baris.findViewById<Button>(R.id.btnTambah)
        val decrementButton = baris.findViewById<Button>(R.id.btnMinus)
        val tvCounter = baris.findViewById<TextView>(R.id.tvCounter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerandaViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_bunga,parent,false)
        return BerandaViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BerandaViewHolder, position: Int) {
        val binding = list[position]

        holder.tvNamaBunga.text = binding.nama
        holder.tvHargaBunga.text = "Harga Mulai Dari Rp ${binding.harga.toString()}"
        holder.tvStockBunga.text = " Stock ${binding.stok.toString()}"
        holder.gambarBunga.setImageResource(binding.gambarResId)

        holder.incrementButton.setOnClickListener {
            viewmodel.increment(position)
            notifyDataSetChanged()
        }

        holder.decrementButton.setOnClickListener {
            viewmodel.decrement(position,konteks)
            notifyDataSetChanged()
        }



        holder.tvCounter.text = binding.counter.toString()







    }


}