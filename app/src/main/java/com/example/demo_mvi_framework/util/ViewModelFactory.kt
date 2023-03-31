package com.example.demo_mvi_framework.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo_mvi_framework.data.api.ApiHelper
import com.example.demo_mvi_framework.data.repository.MainRepository
import com.example.demo_mvi_framework.ui.mvi.viewmodel.MVIViewModel

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MVIViewModel::class.java)) {
            return MVIViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
