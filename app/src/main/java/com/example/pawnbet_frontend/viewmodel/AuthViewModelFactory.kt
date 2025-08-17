package com.example.pawnbet_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager

class AuthViewModelFactory(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(api, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
