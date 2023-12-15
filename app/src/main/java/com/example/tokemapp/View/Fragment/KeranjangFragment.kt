package com.example.tokemapp.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokemapp.Model.Adapter.AdapterKeranjang
import com.example.tokemapp.Model.KeranjangModel
import com.example.tokemapp.Model.listkeranjang
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.KeranjangViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class KeranjangFragment : Fragment() {
    private lateinit var recyler: RecyclerView
    val keranjangViewModel: KeranjangViewModel by activityViewModels()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var progress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keranjang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        recyler = view.findViewById(R.id.recylerKeranjang)
        progress = view.findViewById(R.id.progressBar)

        GlobalScope.launch { getDataFromFirestore() }
        keranjangViewModel.listKeranjang.observe(viewLifecycleOwner){newValue ->
            recyler.layoutManager  = LinearLayoutManager(requireContext())
            recyler.adapter = AdapterKeranjang(newValue,requireContext())
            if (newValue.size != 0){
                progress.visibility = View.GONE
            }
        }

    }

    suspend fun getDataFromFirestore(){
        val database = firestore.collection("kembang").get().await()
        withContext(Dispatchers.IO){
            database?.let {document ->
             val listKEranjangFirestore = document.map { doc ->
                 KeranjangModel(
                     doc.getString("namaBunga")?:"",
                     (doc["jumlah"] as? Number)?.toInt()?:0,
                     (doc["harga"] as? Number)?.toInt()?:0,
                     doc.getString("namaPembeli")?:"",
                     doc.getString("alamat")?:"",
                     doc.getString("nomorHp")?:""
                 )

             }

                keranjangViewModel._listKeranjang.postValue(listKEranjangFirestore.toMutableList())

            }
        }
    }
}