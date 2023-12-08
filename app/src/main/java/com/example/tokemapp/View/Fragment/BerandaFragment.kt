package com.example.tokemapp.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokemapp.Model.Adapter.AdapterBeranda
import com.example.tokemapp.Model.listBunga
import com.example.tokemapp.R
import com.example.tokemapp.ViewModel.BungaViewModel


class BerandaFragment : Fragment() {
    lateinit var recylerBeranda: RecyclerView
    val bungaViewModel : BungaViewModel by activityViewModels()
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

        bungaViewModel.listBungacuy.observe(viewLifecycleOwner){newValue ->
            recylerBeranda.layoutManager = LinearLayoutManager(requireContext())
            recylerBeranda.adapter = AdapterBeranda(listBunga,requireContext(),bungaViewModel,requireActivity().supportFragmentManager)
        }


    }
}