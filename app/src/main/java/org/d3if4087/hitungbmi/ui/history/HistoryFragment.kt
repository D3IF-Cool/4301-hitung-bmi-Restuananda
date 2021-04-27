package org.d3if4087.hitungbmi.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if4087.hitungbmi.databinding.FragmentHistoryBinding
import org.d3if4087.hitungbmi.db.BmiDb

class HistoryFragment: Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory = HistoryViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, {
            Log.d("HistoryFragment", "Jumlah data : ${it.size}")
        })
    }
}