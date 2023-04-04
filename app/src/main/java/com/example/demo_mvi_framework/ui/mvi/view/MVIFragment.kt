package com.example.demo_mvi_framework.ui.mvi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_mvi_framework.R
import com.example.demo_mvi_framework.data.api.ApiHelperImpl
import com.example.demo_mvi_framework.data.api.RetrofitBuilder
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.FragmentMVIBinding
import com.example.demo_mvi_framework.ui.main.userdetail.UserDetailAdapter
import com.example.demo_mvi_framework.ui.main.userdetail.UserDetailFragment
import com.example.demo_mvi_framework.ui.mvi.intent.MVIIntent
import com.example.demo_mvi_framework.ui.mvi.viewmodel.MVIViewModel
import com.example.demo_mvi_framework.ui.mvi.viewstate.MVIState
import com.example.demo_mvi_framework.util.ViewModelFactory
import kotlinx.coroutines.launch

class MVIFragment : Fragment() {
    private var _binding: FragmentMVIBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MVIViewModel
    private lateinit var adapter: UserDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMVIBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        initView()
        handleEvents()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToDetails(user: User) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, UserDetailFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }

    private fun handleEvents() {
        binding.btnMVIFetchUsers.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(MVIIntent.FetchUser)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            binding.run {
                viewModel.state.collect {
                    when (it) {
                        is MVIState.Idle -> {
                            Log.i("TAG", "observeViewModel: IDLE dayyyy")
                        }
                        is MVIState.Loading -> {
                            btnMVIFetchUsers.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                        is MVIState.Users -> {
                            btnMVIFetchUsers.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            Log.i("TAG", "observeViewModel: ${viewModel.getUsers().size}")
                            displayList()
                        }
                        is MVIState.Error -> {
                            btnMVIFetchUsers.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Toast.makeText(context, it.Error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun displayList() {
        binding.rvUsers.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        adapter = UserDetailAdapter(viewModel.getUsers()) {
            goToDetails(it)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService)
            )
        )[MVIViewModel::class.java]
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
            adapter = this@MVIFragment.adapter
        }
    }
}
