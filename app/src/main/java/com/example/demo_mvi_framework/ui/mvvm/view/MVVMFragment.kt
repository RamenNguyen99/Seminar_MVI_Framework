package com.example.demo_mvi_framework.ui.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_mvi_framework.R
import com.example.demo_mvi_framework.data.api.ApiHelperImpl
import com.example.demo_mvi_framework.data.api.RetrofitBuilder
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.FragmentMVVMBinding
import com.example.demo_mvi_framework.ui.main.userdetail.UserDetailAdapter
import com.example.demo_mvi_framework.ui.main.userdetail.UserDetailFragment
import com.example.demo_mvi_framework.ui.mvvm.viewmodel.MVVMViewModel
import com.example.demo_mvi_framework.util.ViewModelFactory
import kotlinx.coroutines.launch

class MVVMFragment : Fragment() {
    private var _binding: FragmentMVVMBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserDetailAdapter

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
        initViewModel()
        initAdapter()
        initView()
        handleEvents()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collect {
                    if (it.isFetchedAPI) {
                        displayList()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingState.collect {
                    binding.progressBar.visibility =
                        if (it.isShowLoading) View.VISIBLE else View.GONE
                    binding.btnMVVMFetchUsers.visibility =
                        if (it.isShowFetchUserButton) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayList() {
        binding.rvUsers.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
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
            viewModel.fetchUsers()
        }
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
}
