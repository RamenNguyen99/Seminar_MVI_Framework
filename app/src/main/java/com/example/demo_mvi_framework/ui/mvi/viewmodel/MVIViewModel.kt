package com.example.demo_mvi_framework.ui.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.data.repository.MainRepository
import com.example.demo_mvi_framework.ui.mvi.intent.MVIIntent
import com.example.demo_mvi_framework.ui.mvi.viewstate.MVIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
class MVIViewModel(
    private val repository: MainRepository
) : ViewModel() {
    val userIntent = Channel<MVIIntent>(Channel.UNLIMITED)

    private val users = mutableListOf<User>()

    private val _state = MutableStateFlow<MVIState>(MVIState.Idle)
    val state: StateFlow<MVIState>
        get() = _state

    init {
        handleIntent()
    }

    internal fun getUsers() = users

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MVIIntent.FetchUser -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = MVIState.Loading
            _state.value = try {
                repository.getUsers().collect {
                    users.clear()
                    users.addAll(it)
                }
                MVIState.Users(users)
            } catch (e: Exception) {
                MVIState.Error(e.localizedMessage)
            }
        }
    }
}
