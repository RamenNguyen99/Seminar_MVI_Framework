package com.example.demo_mvi_framework.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demo_mvi_framework.R
import com.example.demo_mvi_framework.databinding.FragmentMainBinding
import com.example.demo_mvi_framework.ui.mvi.view.MVIFragment
import com.example.demo_mvi_framework.ui.mvvm.view.MVVMFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnMVI.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContainer, MVIFragment())
                    .addToBackStack(null)
                    .commit()
            }
            btnMVVM.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContainer, MVVMFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
