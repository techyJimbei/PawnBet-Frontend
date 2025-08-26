package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.LoginRequest
import com.example.pawnbet_frontend.model.SignUpRequest
import kotlinx.coroutines.launch

class AuthViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
): ViewModel() {


    var username by mutableStateOf<String?>(null)
        private set

    var profilePicture by mutableStateOf<String?>(null)
        private set


    fun signup(email: String, username: String, password: String, profileImageUrl: String) {
        viewModelScope.launch {
            try {
                val response = api.signup(SignUpRequest(email, username, password, profileImageUrl))
                if (response.isSuccessful) {
                    Log.d("Auth", "Sign up successful")
                } else {
                    Log.e("Auth", "Sign up failed: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Sign Up failed", e)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = api.login(LoginRequest(username, password))
                if (response.isSuccessful && response.body() != null && !response.body()!!.token.isNullOrEmpty()) {
                    Log.e("Auth", "Login Successful")
                    tokenManager.saveToken(response.body()!!.token)
                } else {
                    Log.e("Auth", "Login Failed: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Cannot Login: ${e.message}")
            }
        }
    }

    suspend fun verifyToken(): Boolean {
        val token = tokenManager.getToken()
        return if (token.isNullOrEmpty()) {
            false
        } else {
            try {
                api.verifyToken("Bearer $token")
            } catch (e: Exception) {
                false
            }
        }
    }


    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken()
                if (token.isNullOrEmpty()) {
                    Log.e("Auth", "No JWT token found")
                }
                val authHeader = "Bearer $token"
                val response = api.getUserProfile(authHeader)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        username = body.username
                        profilePicture = body.profileImageUrl
                        Log.d("Auth", "Profile fetched successfully")
                    }
                } else {
                    Log.e("Auth", "Profile fetch failed: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Failed to fetch profile", e)
            }
        }
    }
}
