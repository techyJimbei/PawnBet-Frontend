package com.example.pawnbet_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager

class OrderViewModelFactory(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(api, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}