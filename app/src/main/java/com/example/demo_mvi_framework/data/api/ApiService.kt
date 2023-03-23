package com.example.demo_mvi_framework.data.api

import com.example.demo_mvi_framework.data.model.User
import retrofit2.http.GET

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
