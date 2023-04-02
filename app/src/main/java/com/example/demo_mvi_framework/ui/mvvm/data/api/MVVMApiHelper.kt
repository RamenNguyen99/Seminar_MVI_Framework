package com.example.demo_mvi_framework.ui.mvvm.data.api

import com.example.demo_mvi_framework.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by tuong.nguyen2 on 02/04/2023.
 */
interface MVVMApiHelper {
    fun getUsers(): Flow<List<User>>
}
