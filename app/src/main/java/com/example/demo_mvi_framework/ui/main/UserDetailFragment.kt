package com.example.demo_mvi_framework.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {
    companion object {
        const val USER = "USER"

        fun newInstance(user: User) =
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(USER, user)
                }
            }
    }

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable(USER) as User
        initView(user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(user: User?) {
        binding.run {
            Glide.with(imgAvatar.context).load(user?.avatar).into(imgAvatar)
            tvUserName.text = user?.name
            tvEmail.text = user?.email
        }
    }
}
