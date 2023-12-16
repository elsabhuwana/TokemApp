package com.example.tokemapp.Model.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokemapp.Model.InboxModel
import com.example.tokemapp.R

class AdapterInbox(val list: List<InboxModel>): RecyclerView.Adapter<AdapterInbox.InboxViewHolder>() {

    class InboxViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNamaPenjual = baris.findViewById<TextView>(R.id.tvNamaPenjual)
        val tvPesanPenjual = baris.findViewById<TextView>(R.id.tvTextPenjual)
        val tvWaktuPesan = baris.findViewById<TextView>(R.id.tvWaktuPesan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_pesan,parent,false)
        return InboxViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
        val bind = list[position]

        holder.tvNamaPenjual.text = bind.nama
        holder.tvWaktuPesan.text = bind.waktu
        holder.tvPesanPenjual.text = bind.pesan
    }
}