package com.example.tokemapp.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.tokemapp.Model.UserModel
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ReviewFragment : Fragment() {
    private lateinit var etJudul: EditText
    private lateinit var etReview: EditText
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnPost: Button
    private lateinit var btnCancel: Button
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etJudul = view.findViewById(R.id.etJudulReview)
        etReview = view.findViewById(R.id.etReviewProduk)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        btnPost = view.findViewById(R.id.btnPostReview)
        btnCancel = view.findViewById(R.id.btnCancelReview)

        val idBunga = arguments?.getString("idBunga")

        GlobalScope.launch { getUserDataFromFirestore() }

        btnCancel.setOnClickListener {
           requireActivity().supportFragmentManager.popBackStack()
        }
        userViewModel.userReview.observe(viewLifecycleOwner){newValue ->
            btnPost.setOnClickListener {
                if (newValue.size == 0){
                    Toast.makeText(requireContext(), "Tunggu Dulu", Toast.LENGTH_SHORT).show()
                }else{
                    val judul = etJudul.text
                    val review = etReview.text
                    addDataReview(idBunga!!,userViewModel.userReview.value?.get(0)!!.nama,review.toString(),judul.toString())
                }
            }

        }



    }

    suspend fun getUserDataFromFirestore(){
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
                userViewModel._userReview.postValue(user.toMutableList())

            }
        }
    }

    fun addDataReview(id: String,nama: String,review: String,judul:String){
        val reviewBos = hashMapOf(
            "nama" to nama,
            "judul" to judul,
            "review" to review
        )
        firestore.collection("bunga").document(id).collection("review").add(reviewBos).addOnSuccessListener {
            Toast.makeText(requireContext(), "Berhasil Menambahkan Review", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal Menambahkan Review", Toast.LENGTH_SHORT).show()
        }
    }

}