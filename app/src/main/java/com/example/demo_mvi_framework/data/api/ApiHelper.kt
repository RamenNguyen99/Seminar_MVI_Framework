package com.example.demo_mvi_framework.data.api

import com.example.demo_mvi_framework.data.model.User

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
interface ApiHelper {
    suspend fun getUsers(): List<User>
}
