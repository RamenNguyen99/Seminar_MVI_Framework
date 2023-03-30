package com.example.demo_mvi_framework.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_mvi_framework.data.api.ApiHelperImpl
import com.example.demo_mvi_framework.data.api.RetrofitBuilder
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.ActivityMainBinding
import com.example.demo_mvi_framework.ui.main.adapter.MainAdapter
import com.example.demo_mvi_framework.ui.main.intent.MainIntent
import com.example.demo_mvi_framework.ui.main.viewmodel.MainViewModel
import com.example.demo_mvi_framework.ui.main.viewstate.MainState
import com.example.demo_mvi_framework.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf()) {
        Log.i("TAG", "Call back receiver: $it")
        goToDetails(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
        observeViewModel()
        handleEvents()
    }

    private fun goToDetails(user: User) {
        startActivity(Intent(this, UserDetailActivity::class.java).apply {
            putExtra("USER", user)
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            binding.run {
                mainViewModel.state.collect {
                    when (it) {
                        is MainState.Idle -> {
                            Log.i("TAG", "observeViewModel: IDLE dayyyy")
                        }
                        is MainState.Loading -> {
                            btnFetchUsers.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                        is MainState.Users -> {
                            btnFetchUsers.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            displayList(it.users)
                        }
                        is MainState.Error -> {
                            btnFetchUsers.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@MainActivity, it.Error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun displayList(users: List<User>) {
        binding.rvContainer.visibility = View.VISIBLE
        adapter.run {
            addData(users)
            notifyDataSetChanged()
        }
    }

    private fun handleEvents() {
        binding.btnFetchUsers.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService)
            )
        )[MainViewModel::class.java]
    }

    private fun initView() {
        binding.rvContainer.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = this@MainActivity.adapter
        }
    }
}
