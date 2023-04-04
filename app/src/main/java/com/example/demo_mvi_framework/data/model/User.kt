package com.example.demo_mvi_framework.data.model

import com.squareup.moshi.Json

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
data class User(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "email") val email: String = "",
    @Json(name = "avatar") val avatar: String = ""
) : java.io.Serializable
