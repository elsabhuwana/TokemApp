package com.example.tokemapp.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.PembelianViewModel
import com.google.firebase.firestore.FirebaseFirestore


class PembelianFragment : Fragment() {
    private lateinit var tvJudulBunga: TextView
    private lateinit var tvDeskripsiBunga: TextView
    private lateinit var tvCounterBeli : TextView
    private lateinit var tvHargaTotal : TextView
    private lateinit var btnKembali : Button
    private lateinit var btnIncrement: Button
    private lateinit var btnDecrement : Button
    private lateinit var btnBeli : Button
    private lateinit var etNamaPembeli : EditText
    private lateinit var etAlamatPembeli : EditText
    private lateinit var etNomorHpPembeli : EditText
    private lateinit var firestore: FirebaseFirestore
    val pembelianViewModel: PembelianViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pembelian, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        tvJudulBunga = view.findViewById(R.id.tvNamaBungaBeli)
        tvDeskripsiBunga = view.findViewById(R.id.tvDeskripsiBungaBeli)
        tvCounterBeli = view.findViewById(R.id.tvCounterBeli)
        tvHargaTotal = view.findViewById(R.id.tvHargaTotalBeli)
        btnKembali = view.findViewById(R.id.KembaliBeli)
        btnIncrement = view.findViewById(R.id.btnIncrementBeli)
        btnDecrement = view.findViewById(R.id.btnDecrementBeli)
        etNamaPembeli = view.findViewById(R.id.editNamaBeli)
        etAlamatPembeli = view.findViewById(R.id.editAlamatBeli)
        etNomorHpPembeli = view.findViewById(R.id.editNomorHpBeli)
        btnBeli = view.findViewById(R.id.btnBeli)

        val judul = arguments?.getString("judul")
        val deskripsi = arguments?.getString("deskripsi")
        val harga = arguments?.getInt("harga")
        val counter = arguments?.getInt("counter")
        val gambar = arguments?.getString("gambar")

        pembelianViewModel.setAwalCounter(counter!!)



        tvJudulBunga.text = judul.toString()
        tvDeskripsiBunga.text = deskripsi.toString()

        SetCounter(harga!!)
        viewHargaTotal()



        btnBeli.setOnClickListener {
            val namaPembeli = etNamaPembeli.text.toString()
            val alamatPembeli = etAlamatPembeli.text.toString()
            val nomorHp = etNomorHpPembeli.text.toString()

            if (namaPembeli.isEmpty() || alamatPembeli.isEmpty() || nomorHp.isEmpty()){
                Toast.makeText(requireContext(), "Silahkan Isi Semua Field", Toast.LENGTH_SHORT).show()
            }else{
                addDataToFirestore(namaPembeli,alamatPembeli,nomorHp,judul!!,deskripsi!!,"https://2.bp.blogspot.com/-AdFojCFxcik/VGGRGfz2-7I/AAAAAAAAAnE/LjwSW-L1EKU/s1600/Bunga%2BKamboja.jpg")
            }
        }

        btnKembali.setOnClickListener {
            pembelianViewModel.resetCounter()
          requireActivity().supportFragmentManager.popBackStack()



        }






    }

    fun SetCounter(hargaSatuan: Int){
        pembelianViewModel.setHarga(pembelianViewModel.counter.value!!,hargaSatuan)
        btnIncrement.setOnClickListener {
            pembelianViewModel.increment(hargaSatuan)
        }
        btnDecrement.setOnClickListener {
            pembelianViewModel.decrement(requireContext(),hargaSatuan)
        }
    }

    fun viewHargaTotal(){
        pembelianViewModel.harga.observe(viewLifecycleOwner){newValue ->
            tvHargaTotal.text = newValue.toString()
        }

        pembelianViewModel.counter.observe(viewLifecycleOwner){newValue ->
            tvCounterBeli.text = newValue.toString()
        }
    }

   fun addDataToFirestore(tvNama: String,tvAlamat: String,tvNomorHp:String,tvNamaBunga: String,
                          tvDeskripsiBunga: String,
                          gambarBunga: String){
       val data = hashMapOf(
           "namaBunga" to  tvNamaBunga,
           "deskripsi" to  tvDeskripsiBunga,
           "gambar" to gambarBunga,
           "namaPembeli" to tvNama,
           "alamat" to tvAlamat,
           "nomorHp" to tvNomorHp,
           "harga" to pembelianViewModel.harga.value,
           "jumlah" to pembelianViewModel.counter.value
       )
        firestore.collection("kembang").add(data).addOnSuccessListener {
            Toast.makeText(requireContext(), "Berhasil Menambahkan Ke Keranjang", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal Menambahkan Ke Keranjang", Toast.LENGTH_SHORT).show()
        }
    }


}