package com.example.demo_mvi_framework.ui.mvvm.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_mvi_framework.R
import com.example.demo_mvi_framework.data.api.RetrofitBuilder
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.FragmentMVVMBinding
import com.example.demo_mvi_framework.ui.main.userdetail.UserDetailAdapter
import com.example.demo_mvi_framework.ui.main.userdetail.UserDetailFragment
import com.example.demo_mvi_framework.ui.mvvm.data.api.MVVMApiHelperImpl
import com.example.demo_mvi_framework.ui.mvvm.viewmodel.MVVMViewModel
import com.example.demo_mvi_framework.util.ViewModelFactory

class MVVMFragment : Fragment() {
    private var _binding: FragmentMVVMBinding? = null
    private val binding get() = _binding!!
    private var adapter = UserDetailAdapter(arrayListOf()) {
        Log.i("TAG", "Call back receiver: $it")
        goToDetails(it)
    }
    private lateinit var viewModel: MVVMViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMVVMBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        handleEvents()
    }

    private fun goToDetails(user: User) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, UserDetailFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }

    private fun handleEvents() {
        binding.btnMVVMFetchUsers.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
//            if (viewModel.users!= null) {
                viewModel.users?.let {
                    Log.i("TAG", "handleEvents: da co users")
                    displayList(it)
                }
//            }
        }
    }

    private fun displayList(users: List<User>) {
        binding.run {
            rvUsers.visibility = View.VISIBLE
            btnMVVMFetchUsers.visibility = View.GONE
            progressBar.visibility = View.GONE
            adapter.run {
                addData(users)
                notifyDataSetChanged()
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                null,
                MVVMApiHelperImpl(RetrofitBuilder.apiService)
            )
        )[MVVMViewModel::class.java]
    }

    private fun initView() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = this@MVVMFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
