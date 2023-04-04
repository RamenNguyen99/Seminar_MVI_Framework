package com.example.demo_mvi_framework.data.api

import com.example.demo_mvi_framework.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override fun getUsers(): Flow<List<User>> = flow {
        emit(apiService.getUsers())
    }
}
