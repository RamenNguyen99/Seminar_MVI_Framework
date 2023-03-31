package com.example.demo_mvi_framework.ui.mvi.viewstate

import com.example.demo_mvi_framework.data.model.User

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
sealed class MVIState {
    object Idle: MVIState()
    object Loading: MVIState()
    data class Users(val users: List<User>): MVIState()
    data class Error(val Error: String?): MVIState()
}
