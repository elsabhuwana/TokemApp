package com.example.tokemapp.View.Fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tokemapp.Model.Adapter.AdapterReview
import com.example.tokemapp.Model.ReviewModel
import com.example.tokemapp.Model.listReview
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.ReviewViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class DetailBungaFragment : Fragment() {
    lateinit var tvDeskripsi : TextView
    lateinit var tvHarga : TextView
    lateinit var recylerViewReview: RecyclerView
    lateinit var btnAddAchar: Button
    lateinit var btnAddReview: Button
    lateinit var gambarBunga: ImageView
    lateinit var firestore: FirebaseFirestore
    val reviewViewModel: ReviewViewModel by activityViewModels()

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
        gambarBunga = view.findViewById(R.id.gambarBungaDetail)
        firestore = FirebaseFirestore.getInstance()


        val judul = arguments?.getString("judul")
        val deskripsi = arguments?.getString("deskripsi")
        val harga = arguments?.getInt("harga")
        val counter = arguments?.getInt("counter")
        val gambar = arguments?.getString("gambar")
        val id = arguments?.getString("id")

        Glide.with(requireContext()).load(gambar).placeholder(R.drawable.lili).into(gambarBunga)

        tvDeskripsi.text = deskripsi

        tvDeskripsi.movementMethod = ScrollingMovementMethod()
        tvHarga.text = "Rp. ${harga.toString()}"

        GlobalScope.launch { getDataReviewFirestore(id!!) }

        btnAddReview.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idBunga",id)
            val reviewFragment = ReviewFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            reviewFragment.arguments =bundle
            transaksi.replace(R.id.fragmentContainerView,reviewFragment)
            transaksi.addToBackStack(null)
            transaksi.commit()
        }

        btnAddAchar.setOnClickListener {
            val bundle =Bundle()
            bundle.putString("judul",judul)
            bundle.putString("deskripsi",deskripsi)
            bundle.putInt("harga",harga!!.toInt())
            bundle.putInt("counter",counter!!)
            bundle.putString("gambar",gambar)
            val keranjangFragment = PembelianFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            keranjangFragment.arguments = bundle
            transaksi.replace(R.id.fragmentContainerView,keranjangFragment)
            transaksi.addToBackStack(null)

            transaksi.commit()
        }

        reviewViewModel.listReview.observe(viewLifecycleOwner){newValue ->
            recylerViewReview.layoutManager = LinearLayoutManager(requireContext())
            recylerViewReview.adapter = AdapterReview(newValue,requireContext())
        }

    }

    suspend fun getDataReviewFirestore(id: String){
        val database = firestore.collection("bunga").document(id).collection("review").get().await()
        withContext(Dispatchers.IO){
            database?.let { document ->
                val listReview = document.map { doc ->
                    ReviewModel(
                        doc.getString("nama")?:"",
                        doc.getString("judul")?:"",
                        doc.getString("review")?:""
                    )
                }

                reviewViewModel._listReview.postValue(listReview.toMutableList())



            }
        }
    }
}