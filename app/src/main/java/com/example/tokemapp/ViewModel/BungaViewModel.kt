package com.example.tokemapp.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokemapp.Model.ListBunga
import com.example.tokemapp.Model.listBunga

class BungaViewModel : ViewModel() {
    var kategori: MutableLiveData<String> = MutableLiveData("")
    var _listBunga:MutableLiveData<MutableList<ListBunga>> = MutableLiveData<MutableList<ListBunga>>(
        listBunga
    )
    var searchQuery: MutableLiveData<String> = MutableLiveData("")
    val listBungacuy:LiveData<MutableList<ListBunga>>
        get() = _listBunga

    val getSearchQuery: LiveData<String>
        get() = searchQuery

    fun increment(posisi: Int){
        _listBunga.value?.get(posisi)!!.counter+=1
    }

    val getKategori: LiveData<String>
        get() = kategori

    fun decrement(posisi: Int,konteks: Context){
        if (_listBunga.value?.get(posisi)!!.counter <= 0){
            Toast.makeText(konteks, "Tidak Bisa Dikurangi Lagi", Toast.LENGTH_SHORT).show()
        }else{
            _listBunga.value?.get(posisi)!!.counter-=1
        }
    }
}