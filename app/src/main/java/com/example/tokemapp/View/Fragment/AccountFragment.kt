package com.example.tokemapp.View.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.tokemapp.Model.UserModel
import com.example.tokemapp.R
import com.example.tokemapp.View.Activity.LoginActivity
import com.example.tokemapp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AccountFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnLogout: Button
    private lateinit var firestore: FirebaseFirestore
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var tvNamaUSer: TextView
    private lateinit var tvEmailUser: TextView
    private lateinit var btnEditProfileBawah: Button
    private lateinit var btnEditProfileAtas: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogout = view.findViewById(R.id.btnLogoutAccount)
        tvEmailUser = view.findViewById(R.id.tvEmailUser)
        tvNamaUSer = view.findViewById(R.id.tvNamaUser)
        btnEditProfileAtas = view.findViewById(R.id.btnEditTopAccount)
        btnEditProfileBawah = view.findViewById(R.id.btnEditMyAccount)


        firebaseAuth  = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        GlobalScope.launch { getUserFromFirestore() }

        btnLogout.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Konfirmasi")
            dialog.setMessage("Apakah Anda Ingin Log out")
            dialog.setPositiveButton("Keluar") { _, i ->
                keluar()
            }
            dialog.setNegativeButton("Batal") { dialog, i ->
                dialog.dismiss()
            }
            dialog.show()

        }

        userViewModel.user.observe(viewLifecycleOwner){newValue ->
            if (newValue.size == 0){
                tvNamaUSer.text = "Tunggu Dulu"
                tvEmailUser.text = "Tunggu Dulu"
            }else{
                tvNamaUSer.text = newValue[0].nama
                tvEmailUser.text = newValue[0].email
                toEditFragment(newValue[0].nama,newValue[0].tanggalLahir,newValue[0].password,newValue[0].id)
            }

        }
    }

    fun keluar(){
        firebaseAuth.signOut()
        val intent = Intent(requireContext(),LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    suspend fun getUserFromFirestore(){
        val dataUser = firestore.collection("user").whereEqualTo("email",firebaseAuth.currentUser!!.email.toString()).get().await()
        withContext(Dispatchers.IO){
            dataUser?.let {document ->
                val user = document.map { doc ->
                    UserModel(
                        doc.id,
                        doc.getString("email")?:"",
                        doc.getString("nama")?:"",
                        doc.getString("password")?:"",
                        doc.getString("tanggalLahir")?:""
                    )
                }

                userViewModel._user.postValue(user.toMutableList())





            }
        }
    }

    fun toEditFragment(nama: String,tanggalLahir: String,password: String,id: String){
        btnEditProfileBawah.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("nama",nama)
            bundle.putString("tanggal",tanggalLahir)
            bundle.putString("password",password)
            bundle.putString("id",id)
            val toEditFragment = BiodataFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            toEditFragment.arguments = bundle
            transaksi.replace(R.id.fragmentContainerView,toEditFragment)
            transaksi.addToBackStack(null)
            transaksi.commit()

        }

        btnEditProfileAtas.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("nama",nama)
            bundle.putString("tanggal",tanggalLahir)
            bundle.putString("password",password)
            bundle.putString("id",id)
            val toEditFragment = BiodataFragment()
            val transaksi = requireActivity().supportFragmentManager.beginTransaction()
            toEditFragment.arguments = bundle
            transaksi.replace(R.id.fragmentContainerView,toEditFragment)
            transaksi.addToBackStack(null)
            transaksi.commit()

        }


    }
}