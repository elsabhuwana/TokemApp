package com.example.tokemapp.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PembelianViewModel : ViewModel() {
    private var _harga : MutableLiveData<Int> = MutableLiveData(0)
    private var _counterBeli : MutableLiveData<Int> = MutableLiveData(1)

    val harga: LiveData<Int>
        get() = _harga

    val counter: LiveData<Int>
        get() = _counterBeli

    fun setAwalCounter(counterFrag: Int){
        _counterBeli.value = counterFrag
    }
    fun setHarga(counter: Int,hargaSatuan: Int){
        _harga.value =  counter * hargaSatuan
    }

    fun increment(harga: Int){
        _counterBeli.value = _counterBeli.value?.plus(1)
        setHarga(_counterBeli.value!!,harga)
    }
    fun decrement(konteks: Context,harga:Int){
        if (_counterBeli.value!! <= 1){
            Toast.makeText(konteks, "Tidak bisa dikurangi lagi", Toast.LENGTH_SHORT).show()
        }else{
            _counterBeli.value = _counterBeli.value!! - 1
            setHarga(_counterBeli.value!!,harga)
        }
    }

    fun resetCounter(){
        _counterBeli.value = 1
    }
}