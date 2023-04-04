package com.example.demo_mvi_framework.ui.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Created by tuong.nguyen2 on 31/03/2023.
 */
class MVVMViewModel(
    private val repository: MainRepository
) : ViewModel(), VMContract {
    val userState = MutableStateFlow(Result(false, emptyList()))
    val loadingState =
        MutableStateFlow(Loading(isShowLoading = false, isShowFetchUserButton = true))

    private val users = mutableListOf<User>()

    internal fun getUsers() = users

    override fun fetchUsers() {
        viewModelScope.launch {
            loadingState.value = Loading(isShowLoading = true, isShowFetchUserButton = false)
            repository.getUsers()
                .flowOn(Dispatchers.IO)
                .catch {
                    //handle exception
                    loadingState.value =
                        Loading(isShowLoading = false, isShowFetchUserButton = true)
                }
                .collect {
                    users.apply {
                        clear()
                        addAll(it)
                    }
                    loadingState.value =
                        Loading(isShowLoading = false, isShowFetchUserButton = false)
                    userState.value = Result(true, it)
                }
        }
    }
}

data class Result(val isFetchedAPI: Boolean, val users: List<User>)
data class Loading(val isShowLoading: Boolean, val isShowFetchUserButton: Boolean)
