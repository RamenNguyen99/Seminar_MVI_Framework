package com.example.demo_mvi_framework.data.api

import com.example.demo_mvi_framework.data.model.User

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}
