package com.example.demo_mvi_framework.ui.mvvm.viewmodel

import com.example.demo_mvi_framework.data.model.User
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by tuong.nguyen2 on 31/03/2023.
 */
interface VMContract {
    /** use to fetch all user */
    fun fetchUsers()
}
