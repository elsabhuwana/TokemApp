package com.example.tokemapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokemapp.Model.KeranjangModel
import com.example.tokemapp.Model.listkeranjang

class KeranjangViewModel : ViewModel() {
    var _listKeranjang: MutableLiveData<MutableList<KeranjangModel>> = MutableLiveData(listkeranjang)

    val listKeranjang: LiveData<MutableList<KeranjangModel>>
        get() = _listKeranjang
}