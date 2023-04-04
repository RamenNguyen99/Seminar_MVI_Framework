package com.example.demo_mvi_framework.data.api

import com.example.demo_mvi_framework.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
interface ApiHelper {
    fun getUsers(): Flow<List<User>>
}
