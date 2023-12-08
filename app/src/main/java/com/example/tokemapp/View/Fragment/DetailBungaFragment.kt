package com.example.tokemapp.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokemapp.Model.Adapter.AdapterReview
import com.example.tokemapp.Model.listReview
import com.example.tokemapp.R


class DetailBungaFragment : Fragment() {
    lateinit var tvDeskripsi : TextView
    lateinit var tvHarga : TextView
    lateinit var recylerViewReview: RecyclerView
    lateinit var btnAddAchar: Button
    lateinit var btnAddReview: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_bunga, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDeskripsi = view.findViewById(R.id.tvdeskripsiBunga)
        tvHarga = view.findViewById(R.id.tvHargaBunga)
        recylerViewReview = view.findViewById(R.id.recylerReviews)
        btnAddAchar = view.findViewById(R.id.btnAddChart)
        btnAddReview = view.findViewById(R.id.btnTulisReview)

        recylerViewReview.layoutManager = LinearLayoutManager(requireContext())
        recylerViewReview.adapter = AdapterReview(listReview,requireContext())

        val deskripsi = arguments?.getString("deskripsi")
        val harga = arguments?.getInt("harga")

        tvDeskripsi.text = deskripsi
        tvHarga.text = "Rp. ${harga.toString()}"

        btnAddReview.setOnClickListener {
            val reviewFragment = ReviewFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            transaksi.replace(R.id.fragmentContainerView,reviewFragment)
            transaksi.addToBackStack(null)
            transaksi.commit()
        }

        btnAddAchar.setOnClickListener {
            val keranjangFragment = PembelianFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            transaksi.replace(R.id.fragmentContainerView,keranjangFragment)
            transaksi.addToBackStack(null)
            transaksi.commit()
        }

    }
}