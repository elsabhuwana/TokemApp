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
import com.example.tokemapp.Model.Adapter.AdapterBeranda
import com.example.tokemapp.Model.ListBunga
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.BungaViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class BerandaFragment : Fragment() {
    lateinit var recylerBeranda: RecyclerView
    val bungaViewModel : BungaViewModel by activityViewModels()
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recylerBeranda = view.findViewById(R.id.recyclerberanda)
        firestore = FirebaseFirestore.getInstance()

        GlobalScope.launch { getDataBunganFirestore() }

        bungaViewModel.listBungacuy.observe(viewLifecycleOwner){newValue ->
            recylerBeranda.layoutManager = LinearLayoutManager(requireContext())
            recylerBeranda.adapter = AdapterBeranda(newValue,requireContext(),bungaViewModel,requireActivity().supportFragmentManager)
            if (newValue.size != 0){

            }
        }


    }

    suspend fun getDataBunganFirestore(){
        val database = firestore.collection("bunga").get().await()
        withContext(Dispatchers.IO){
            database?.let {document ->
                val listBunga = document.map {doc ->
                    ListBunga(
                        doc.id,
                        doc.getString("Nama_bunga")?: "",
                        doc.getString("Gambar")?:"",
                        doc.getString("Deskripsi")?:"",
                        (doc["Harga"] as? Number)?.toInt()?: 0,
                        (doc["Stok"] as? Number)?.toInt()?:0
                    )

                }

                bungaViewModel._listBunga.postValue(listBunga.toMutableList())



            }
        }
    }
}