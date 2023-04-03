package com.example.demo_mvi_framework.ui.mvvm.data.api

import com.example.demo_mvi_framework.data.api.ApiService
import com.example.demo_mvi_framework.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by tuong.nguyen2 on 02/04/2023.
 */
class MVVMApiHelperImpl(private val apiService: ApiService): MVVMApiHelper {
    override fun getUsers(): Flow<List<User>> = flow {
        emit(apiService.getUsers())
    }
}
