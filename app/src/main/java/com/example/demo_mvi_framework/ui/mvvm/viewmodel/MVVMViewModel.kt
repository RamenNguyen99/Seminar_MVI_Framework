package com.example.demo_mvi_framework.ui.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.ui.mvvm.data.api.MVVMApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Created by tuong.nguyen2 on 31/03/2023.
 */
class MVVMViewModel(
    private val apiHelper: MVVMApiHelper
) : ViewModel(), VMContract {
    var users: List<User>? = null

    init {
        fetchUsers()
    }

    override fun fetchUsers() {
        viewModelScope.launch {
            apiHelper.getUsers()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    //handle exception
                }
                .collect {
                    users = it
                }
        }
    }
}
