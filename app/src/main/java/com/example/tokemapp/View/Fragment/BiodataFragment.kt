package com.example.tokemapp.View.Fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tokemapp.Model.UserModel
import com.example.tokemapp.Model.UserUpdate
import com.example.tokemapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User


class BiodataFragment : Fragment() {
    private lateinit var etNama: EditText
    private lateinit var etUpdatePassword: EditText
    private lateinit var etUpdateTanggalLahir: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var btnUpdateProfile: ImageView
    private lateinit var tvEmailBio: TextView
    private lateinit var btnBackBiodata: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biodata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etNama = view.findViewById(R.id.etNamaLengkapBiodata)
        etUpdatePassword = view.findViewById(R.id.etUpdatePasswordBiodata)
        etUpdateTanggalLahir = view.findViewById(R.id.etTanggalLahirBiodata)
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile)
        tvEmailBio = view.findViewById(R.id.tvEmailBiodata)
        btnBackBiodata = view.findViewById(R.id.btnBackBiodata)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val nama = arguments?.getString("nama")
        val tanggal = arguments?.getString("tanggal")
        val password = arguments?.getString("password")
        val id = arguments?.getString("id")

        etNama.text = nama!!.toEditable()
        etUpdateTanggalLahir.text = tanggal!!.toEditable()
        etUpdatePassword.text = password!!.toEditable()

        tvEmailBio.text = firebaseAuth.currentUser!!.email.toString()

        btnUpdateProfile.setOnClickListener {
            val hasilNama = etNama.text
            val hasilPassword = etUpdatePassword.text
            val hasilTanggal = etUpdateTanggalLahir.text
            updateUserFirebase(hasilPassword.toString(),id.toString(),hasilNama.toString(),hasilTanggal.toString())
        }

        btnBackBiodata.setOnClickListener {
            val toAccountProfile = AccountFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            transaksi.replace(R.id.fragmentContainerView,toAccountProfile)
            transaksi.commit()
        }




    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun updateUserFirebase(password: String,id: String,nama: String,tanggal: String){
        firebaseAuth.currentUser!!.updatePassword(password).addOnSuccessListener {
            val User = UserUpdate(firebaseAuth.currentUser!!.email.toString(),nama,password,tanggal)
            firestore.collection("user").document(id).set(User).addOnSuccessListener {
                Toast.makeText(requireContext(), "Berhasil Update", Toast.LENGTH_SHORT).show()
                val toAccountFragment = AccountFragment()
                val transaksi = requireActivity().supportFragmentManager.beginTransaction()
                transaksi.replace(R.id.fragmentContainerView,toAccountFragment)
                transaksi.commit()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal Update", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal Update", Toast.LENGTH_SHORT).show()
        }
    }
}