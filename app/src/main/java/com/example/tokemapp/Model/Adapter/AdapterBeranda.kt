package com.example.tokemapp.Model.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tokemapp.Model.ListBunga
import com.example.tokemapp.Model.ReviewModel
import com.example.tokemapp.R
import com.example.tokemapp.View.Fragment.DetailBungaFragment
import com.example.tokemapp.View.Fragment.KeranjangFragment
import com.example.tokemapp.View.Fragment.PembelianFragment
import com.example.tokemapp.View.Fragment.ReviewFragment
import com.example.tokemapp.ViewModel.BungaViewModel

class AdapterBeranda (val list:List<ListBunga>, val konteks: Context, val viewmodel: BungaViewModel,val fragment:FragmentManager): RecyclerView.Adapter<AdapterBeranda.BerandaViewHolder>() {

    class BerandaViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNamaBunga = baris.findViewById<TextView>(R.id.textViewNamaBunga)
        val tvHargaBunga = baris.findViewById<TextView>(R.id.textViewHarga)
        val tvStockBunga = baris.findViewById<TextView>(R.id.textViewStok)
        val gambarBunga = baris.findViewById<ImageView>(R.id.GambarBunga)
        val incrementButton = baris.findViewById<Button>(R.id.btnTambah)
        val decrementButton = baris.findViewById<Button>(R.id.btnMinus)
        val tvCounter = baris.findViewById<TextView>(R.id.tvCounter)
        val btnTambah = baris.findViewById<Button>(R.id.buttonTambah)
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
        Glide.with(konteks).load(binding.gambarResId).placeholder(R.drawable.lili).into(holder.gambarBunga)

        holder.incrementButton.setOnClickListener {
            viewmodel.increment(position)
            notifyDataSetChanged()
        }

        holder.decrementButton.setOnClickListener {
            viewmodel.decrement(position,konteks)
            notifyDataSetChanged()
        }

        holder.gambarBunga.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("judul",binding.nama)
            bundle.putString("deskripsi",binding.deskripsi)
            bundle.putInt("harga",binding.harga)
            bundle.putString("id",binding.id)
            bundle.putString("gambar",binding.gambarResId.toString())
            bundle.putInt("counter",binding.counter)
            val detailFragment = DetailBungaFragment()
            val transaksi = fragment.beginTransaction()
            transaksi.replace(R.id.fragmentContainerView,detailFragment)
            detailFragment.arguments = bundle
            transaksi.addToBackStack(null)
            transaksi.commit()

        }




        holder.tvCounter.text = binding.counter.toString()

        holder.btnTambah.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("counter",binding.counter)
            bundle.putString("judul",binding.nama)
            bundle.putString("deskripsi",binding.deskripsi)
            bundle.putInt("harga",binding.harga)
            bundle.putString("gambar",binding.gambarResId)
            val transaksi = fragment.beginTransaction()
            val toPembelianFrag = PembelianFragment()
            toPembelianFrag.arguments = bundle
            transaksi.replace(R.id.fragmentContainerView,toPembelianFrag)
            transaksi.addToBackStack(null)
            transaksi.commit()
        }


    }


}