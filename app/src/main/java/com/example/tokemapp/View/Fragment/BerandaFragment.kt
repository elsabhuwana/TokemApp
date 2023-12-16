package com.example.tokemapp.View.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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
    private lateinit var Filter_harga:Spinner
    private lateinit var search: Button
    private lateinit var etSearch: EditText

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
        search = view.findViewById(R.id.buttonSearchBerandaFix)
        etSearch  = view.findViewById(R.id.etSearchBeranda)
        recylerBeranda = view.findViewById(R.id.recyclerberanda)
        Filter_harga = view.findViewById(R.id.Filter_harga)
        firestore = FirebaseFirestore.getInstance()




        GlobalScope.launch { getDataBunganFirestore() }

        bungaViewModel.listBungacuy.observe(viewLifecycleOwner){newValue ->
            recylerBeranda.layoutManager = LinearLayoutManager(requireContext())
            recylerBeranda.adapter = AdapterBeranda(newValue,requireContext(),bungaViewModel,requireActivity().supportFragmentManager)
            if (newValue.size != 0){

            }
        }



        Filter_harga.onItemSelectedListener = object : OnItemClickListener,
            AdapterView.OnItemSelectedListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bungaViewModel.kategori.postValue(Filter_harga.selectedItem.toString())
                Log.e("Test",bungaViewModel.kategori.value.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        bungaViewModel.getKategori.observe(viewLifecycleOwner){newValue ->
            if (newValue == "Harga Termahal"){
                GlobalScope.launch { getFilterData(15000) }
            }else if (newValue == "Harga Termurah"){
                GlobalScope.launch { getFilterData(14999) }
            }else{
                GlobalScope.launch { getDataBunganFirestore() }
            }
        }

        search.setOnClickListener {
            val searchText = etSearch.text
            GlobalScope.launch { getSearchDataFirestore(searchText.toString()) }

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
    suspend fun getFilterData(harga: Int){
        if (harga >= 15000){
            val database = firestore.collection("bunga").whereGreaterThan("Harga",harga).get().await()
            withContext(Dispatchers.IO){
                database?.let {document ->
                    val listFilter = document.map {doc ->
                        ListBunga(
                            doc.id,
                            doc.getString("Nama_bunga")?: "",
                            doc.getString("Gambar")?:"",
                            doc.getString("Deskripsi")?:"",
                            (doc["Harga"] as? Number)?.toInt()?: 0,
                            (doc["Stok"] as? Number)?.toInt()?:0
                        )

                    }
                    bungaViewModel._listBunga.postValue(listFilter.toMutableList())

                }
            }
        }else{
            val database = firestore.collection("bunga").whereLessThan("Harga",harga).get().await()
            withContext(Dispatchers.IO){
                database?.let {document ->
                    val listFilter = document.map {doc ->
                        ListBunga(
                            doc.id,
                            doc.getString("Nama_bunga")?: "",
                            doc.getString("Gambar")?:"",
                            doc.getString("Deskripsi")?:"",
                            (doc["Harga"] as? Number)?.toInt()?: 0,
                            (doc["Stok"] as? Number)?.toInt()?:0
                        )

                    }
                    bungaViewModel._listBunga.postValue(listFilter.toMutableList())

                }
            }
        }

    }

    suspend fun getSearchDataFirestore(nama:String){
        val database = firestore.collection("bunga").whereEqualTo("Nama_bunga",nama).get().await()
        withContext(Dispatchers.IO){
            database?.let { document ->
                val listFilterSearch = document.map { doc ->
                    ListBunga(
                        doc.id,
                        doc.getString("Nama_bunga")?: "",
                        doc.getString("Gambar")?:"",
                        doc.getString("Deskripsi")?:"",
                        (doc["Harga"] as? Number)?.toInt()?: 0,
                        (doc["Stok"] as? Number)?.toInt()?:0
                    )
                }
                bungaViewModel._listBunga.postValue(listFilterSearch.toMutableList())
            }
        }
    }

}