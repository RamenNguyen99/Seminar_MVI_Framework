package com.example.demo_mvi_framework.data.repository

import com.example.demo_mvi_framework.data.api.ApiHelper

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
class MainRepository(private val apiHelper: ApiHelper) {
    fun getUsers() = apiHelper.getUsers()
}
