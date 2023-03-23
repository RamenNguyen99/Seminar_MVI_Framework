package com.example.demo_mvi_framework.ui.main.viewstate

import com.example.demo_mvi_framework.data.model.User

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
sealed class MainState {
    object Idle: MainState()
    object Loading: MainState()
    data class Users(val user: List<User>): MainState()
    data class Error(val Error: String?): MainState()
}
