package com.example.tokemapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BungaAdapter(private val listener: BungaItemClickListener) : RecyclerView.Adapter<BungaAdapter.BungaViewHolder>() {

    var flowerList: List<ListBunga> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BungaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_beranda, parent, false)
        return BungaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BungaViewHolder, position: Int) {
        val flower = flowerList[position]
        holder.bind(flower, listener)
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

    inner class BungaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewBunga: ImageView = itemView.findViewById(R.id.GambarBunga)
        private val textViewNamaBunga: TextView = itemView.findViewById(R.id.textViewNamaBunga)
        private val textViewHarga: TextView = itemView.findViewById(R.id.textViewHarga)
        private val textViewStok: TextView = itemView.findViewById(R.id.textViewStok)
        private val numberPicker: NumberPicker = itemView.findViewById(R.id.numberPicker)
        private val buttonTambah: Button = itemView.findViewById(R.id.buttonTambah)

        fun bind(flower: ListBunga, listener: BungaItemClickListener) {
            imageViewBunga.setImageResource(flower.gambarResId)
            textViewNamaBunga.text = flower.nama
            textViewHarga.text = "Harga: Rp. ${flower.harga}"
            textViewStok.text = "Stok: ${flower.stok}"

            // Konfigurasi NumberPicker (tentu saja sesuaikan dengan kebutuhan aplikasi Anda)
            numberPicker.minValue = 1
            numberPicker.maxValue = flower.stok
            numberPicker.wrapSelectorWheel = false

            buttonTambah.setOnClickListener {
                // Panggil metode onItemClick dari listener ketika tombol Tambah diklik
                listener.onItemClick(flower, numberPicker.value)
            }
        }
    }
}
