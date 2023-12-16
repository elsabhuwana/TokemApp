package com.example.tokemapp.Model.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tokemapp.R
import androidx.recyclerview.widget.RecyclerView
import com.example.tokemapp.Model.ReviewModel

class AdapterReview(val listReview: List<ReviewModel>,val konteks: Context):RecyclerView.Adapter<AdapterReview.ReviewViewHolder>() {


    class ReviewViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNama = baris.findViewById<TextView>(R.id.tvNamaOrang)
        val tvJudulReview = baris.findViewById<TextView>(R.id.tvJudulReview)
        val tvReview = baris.findViewById<TextView>(R.id.tvReview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_review,parent,false)
        return ReviewViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val binding = listReview[position]

        holder.tvNama.text = "Nama Pereview : ${binding.nama}"
        holder.tvJudulReview.text = "Judul Review : ${binding.judul}"
        holder.tvReview.text = "Review : ${binding.deskripsi}"

    }
}